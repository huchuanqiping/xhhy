package cn.itcast.core.service;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;

public interface SolrService {

	//返回分页对象(结果集  分页在页面上展示) 本次连接的Solr索引库而不是Mysql
	public Pagination selectPaginationByQuery(Integer pageNo, String keyword, Long brandId, String price) throws Exception;
	
	//加载品牌从Redis中
	public List<Brand> selectBrandListFromRedis();
	
	//保存商品信息到Solr服务器 MQ应用
	public void insertProductToSolr(Long id);
}
