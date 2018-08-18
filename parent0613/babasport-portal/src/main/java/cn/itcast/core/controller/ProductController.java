package cn.itcast.core.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.service.AdService;
import cn.itcast.core.service.CmsService;
import cn.itcast.core.service.SolrService;

/**
 * 商品是首页
 * 搜索
 * 商品详情
 * @author xujie
 *
 */
@Controller
public class ProductController {

	@Autowired
	private SolrService solrService;
	@Autowired
	private AdService adService;
	
	//进入首页   www.jd.com === localhost:8081
	@RequestMapping(value="/")
	public String index(Model model){
		//加载大广告位置的所以广告
		String ads = adService.selectAdList(89L);
		model.addAttribute("ads", ads);
		return "index";
	}
	
	//搜索
	@RequestMapping(value="/search")
	public String search(Integer pageNo, String keyword, Long brandId, String price, Model model) throws Exception{
		//加载品牌Redis中
		List<Brand> brands = solrService.selectBrandListFromRedis();
		model.addAttribute("brands", brands);
		
		//Map  保存查询条件
		Map<String, String> map = new HashMap<>();
		//判断
		if(null != brandId){
			for (Brand brand : brands) {
				if(brandId.equals(brand.getId())){
					map.put("品牌", brand.getName());
					break;
				}
			}
		}
		//价格
		if(null != price){
			if(price.contains("-")){
				map.put("价格", price);
			}else{
				map.put("价格", price + "以上");
			}
		}
		model.addAttribute("map", map);
		
		//返回分页对象(结果集  分页在页面上展示) 本次连接的Solr索引库而不是Mysql
		Pagination pagination = solrService.selectPaginationByQuery(pageNo, keyword, brandId, price);
		model.addAttribute("pagination", pagination);
		model.addAttribute("keyword", keyword);
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		return "search";
	}
	
	@Autowired
	private CmsService cmsService;
	//去商品详情页面
	@RequestMapping(value="/product/detail")
	public String detail(Long id, Model model){
		//通过商品ID 查询商品对象
		Product product = cmsService.selectProductById(id);
		model.addAttribute("product", product);
		//通过商品ID并且库存大于0 查询Sku对象(结果集) Sku里面颜色对象
		List<Sku> skus = cmsService.selectSkuListByProductId(id);
		//去掉重复颜色
		Set<Color> colors = new HashSet<Color>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("colors", colors);
		model.addAttribute("skus", skus);
		return "product";
	}
}
