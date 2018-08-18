package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.pojo.product.Sku;

public interface SkuService {

	//通过商品ID 查询Sku结果集（每一个SKu里颜色对象）
	public List<Sku> selectSkuListByProductId(Long productId);

	//修改
	public void updateSkuById(Sku sku);

}
