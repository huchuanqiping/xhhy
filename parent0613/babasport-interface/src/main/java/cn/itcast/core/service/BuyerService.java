package cn.itcast.core.service;

import cn.itcast.core.pojo.BuyerCart;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.pojo.user.Buyer;

public interface BuyerService {

	//通过用户名查询用户信息
	public Buyer selectBuyerByUsername(String username);

	//通过SkuId 查询sku对象 (里面查询颜色对象 商品对象)
	public Sku selectSkuById(Long id);

	//将购物车追加到Redis中
	public void insertBuyerCartToRedis(BuyerCart buyerCart, String username);

	//获取Redis中的购物车(所有购物车)
	public BuyerCart selectBuyerCartFromRedis(String username);

	//保存订单详情
	public void insertOrder(Order order, String username);
}
