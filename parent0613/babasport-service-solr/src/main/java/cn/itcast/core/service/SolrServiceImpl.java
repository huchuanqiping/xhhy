package cn.itcast.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.dao.product.BrandDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.BrandQuery;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.ProductQuery;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.pojo.product.SkuQuery;
import redis.clients.jedis.Jedis;

/**
 * 搜索
 * @author xujie
 *
 */
@Service("solrService")
public class SolrServiceImpl implements SolrService {

	@Autowired
	private SolrServer solrServer;
	
	//返回分页对象(结果集  分页在页面上展示) 本次连接的Solr索引库而不是Mysql
	public Pagination selectPaginationByQuery(Integer pageNo, String keyword, Long brandId, String price) throws Exception{
		StringBuilder params = new StringBuilder();
		ProductQuery productQuery = new ProductQuery();
		//当前页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//每页数
		productQuery.setPageSize(8);
		//参数
		SolrQuery solrQuery = new SolrQuery();
		//关键词
//		solrQuery.set("q","name_ik:"+keyword);
//		solrQuery.setQuery("name_ik:"+keyword);
		solrQuery.setQuery(keyword);
		params.append("keyword="+keyword);
		//默认查询的域
		solrQuery.set("df", "name_ik");
		//指定域(需要的域)
		solrQuery.set("fl", "id,name_ik,url,price");
		//排序
		solrQuery.setSort("price", ORDER.asc);
		//分页 limit 0,8 当前第一页,每页8条
		solrQuery.setStart(productQuery.getStartRow());
		solrQuery.setRows(productQuery.getPageSize());
		//过滤条件
		//判断品牌ID
		if(null != brandId){
			solrQuery.addFilterQuery("brandId:"+brandId);
			params.append("&brandId=").append(brandId);
		}
		//判断价格区间 0-99 1600
		if(null != price){
			String[] p = price.split("-");
			if(p.length == 2){
				solrQuery.addFilterQuery("price:[" + p[0] + " TO " + p[1] + "]");
			}else{
				solrQuery.addFilterQuery("price:[" + p[0] + " TO *]");
			}
			params.append("&price=").append(price);
		}
		//高亮
		//1.打开高亮的开光
		solrQuery.setHighlight(true);
		//2.设置高亮的字段
		solrQuery.addHighlightField("name_ik");
		//3.设置高亮的前缀
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		//4.设置高亮的后缀
		solrQuery.setHighlightSimplePost("</span>");
		
		//查询索引  执行查询
		QueryResponse response = solrServer.query(solrQuery);
		//取出高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//1:Map K id  V Map
		//2:Map K 需要高亮的域的name_ik  V List
		//3:List<String> 一个域保存多值Solr
		//结果集
		SolrDocumentList docs = response.getResults();
		//总条数
		long numFound = docs.getNumFound();
		//分页对象
		Pagination pagination = new Pagination(
				productQuery.getPageNo(),
				productQuery.getPageSize(),
				(int)numFound
				);
		//商品结果集
		List<Product> products = new ArrayList<>();
		for (SolrDocument doc : docs) {
			Product product = new Product();
			//ID
			String id = (String) doc.get("id");
			product.setId(Long.parseLong(id));
			//名称
			Map<String, List<String>> map = highlighting.get(id);
			List<String> list = map.get("name_ik");
			//String name = (String)doc.get("name_ik");
			product.setName(list.get(0));
			//图片
			String url = (String)doc.get("url");
			product.setImgUrl(url);
			//价格
			product.setPrice((Float)doc.get("price"));
			//添加到结果集中
			products.add(product);
		}
		//结果集
		pagination.setList(products);
		//分页在页面上的展示
		String url = "/search";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	
	@Autowired
	private Jedis jedis;
	
	@Autowired
	private BrandDao brandDao;
	//加载品牌从Redis中
	public List<Brand> selectBrandListFromRedis(){
		
		//从Redis中查询
		Map<String, String> hgetAll = jedis.hgetAll("brand");
		if(null != hgetAll){
			Set<Entry<String,String>> entrySet = hgetAll.entrySet();
			if(null != entrySet && entrySet.size()>0){
				List<Brand> brands = new ArrayList<>();
				//有  直接返回List<Brand>
				for (Entry<String, String> entry : entrySet) {
					Brand brand = new Brand();
					brand.setId(Long.parseLong(entry.getKey()));
					brand.setName(entry.getValue());
					brands.add(brand);
				}
				return brands;
			}
		}
		//没有  从Mysql中查询List<Brand>
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setIsDisplay(1);
		List<Brand> brands = brandDao.selectBrandListByQuery(brandQuery);
		//保存到Redis中一份  800个  连接Redis多少次
		Map<String, String> hash = new HashMap<>();
		for (Brand brand : brands) {
			hash.put(String.valueOf(brand.getId()), brand.getName());
		}
		//连接一次保存一个Map
		jedis.hmset("brand", hash);
		//再返回List
		return brands;
	}
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	//保存商品信息到Solr服务器 MQ应用
	public void insertProductToSolr(Long id){
		//保存商品信息到Solr服务器
		SolrInputDocument doc = new SolrInputDocument();
		//域
		//商品ID
		doc.setField("id", id);
		//商品名称
		Product p = productDao.selectByPrimaryKey(id);
		doc.setField("name_ik", p.getName());
		//商品图片
		doc.setField("url", p.getImgUrl());
		//价格,最便宜的价格
		//select price from bbs_sku where product_id = 443 order by price asc limit 0,1
		SkuQuery skuQuery = new SkuQuery();
		//where product_id = 443
		skuQuery.createCriteria().andProductIdEqualTo(id);
		//order by price asc
		skuQuery.setOrderByClause("price asc");
		//limit 0,1
		skuQuery.setPageNo(1);
		skuQuery.setPageSize(1);
		//指定字段查询,只查询price
		skuQuery.setFields("price");
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		doc.setField("price", skus.get(0).getPrice());
		//品牌ID
		doc.setField("brandId", p.getBrandId());
		//时间
		try {
			solrServer.add(doc);
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
