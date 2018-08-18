package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;

public interface CmsService {

	//通过商品ID  查询商品对象
	public Product selectProductById(Long id);
	
	//通过商品ID并且库存大于0 查询Sku对象(结果集) Sku里面颜色对象
	public List<Sku> selectSkuListByProductId(Long productId);
}
