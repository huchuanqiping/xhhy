package cn.itcast.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.dao.order.DetailDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.dao.user.BuyerDao;
import cn.itcast.core.pojo.BuyerCart;
import cn.itcast.core.pojo.BuyerItem;
import cn.itcast.core.pojo.order.Detail;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.pojo.user.Buyer;
import redis.clients.jedis.Jedis;

/**
 * 用户管理
 * 用户查询
 * 购物车
 * 结算
 * 订单
 * @author xujie
 *
 */
@Service("buyerService")
public class BuyerServiceImpl implements BuyerService{

	@Autowired
	private BuyerDao buyerDao;
	@Autowired
	private Jedis jedis;
	//通过用户名查询用户信息
	public Buyer selectBuyerByUsername(String username){
		Buyer buyer = null;
		//先到Redis中取出此用户名对应的ID
		String id = jedis.get(username);
		if(null != id){
			//再通过此ID查询用户
			buyer = buyerDao.selectByPrimaryKey(Long.parseLong(id));
		}
		return buyer;
	}
	
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	@Autowired
	private ProductDao productDao;
	
	//通过SkuId 查询sku对象 (里面查询颜色对象 商品对象)
	public Sku selectSkuById(Long id) {
		Sku sku = skuDao.selectByPrimaryKey(id);
		sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		sku.setProduct(productDao.selectByPrimaryKey(sku.getProductId()));
		return sku;
	}
	
	//将购物车追加到Redis中
	public void insertBuyerCartToRedis(BuyerCart buyerCart, String username) {
		List<BuyerItem> items = buyerCart.getItems();
		if(items.size() > 0){
			for (BuyerItem item : items) {
				//判断是否存在
				if(jedis.hexists("buyerCart:"+ username, String.valueOf(item.getSku().getId()))){
					//有
					jedis.hincrBy("buyerCart:"+ username, String.valueOf(item.getSku().getId()), item.getAmount());
				}else{
					//没有
					jedis.hset("buyerCart:"+ username, String.valueOf(item.getSku().getId()), item.getAmount() + "");
				}
			}
		}
	}
	
	//获取Redis中的购物车(所有购物车)
	public BuyerCart selectBuyerCartFromRedis(String username) {
		BuyerCart buyerCart = null;
		Map<String, String> hgetAll = jedis.hgetAll("buyerCart:"+ username);
		if(null != hgetAll){
			Set<Entry<String,String>> entrySet = hgetAll.entrySet();
			if(null != entrySet && entrySet.size() > 0){
				buyerCart = new BuyerCart();
				for (Entry<String, String> entry : entrySet) {
					Sku sku = new Sku();
					sku.setId(Long.parseLong(entry.getKey()));
					BuyerItem item = new BuyerItem();
					item.setSku(sku);
					item.setAmount(Integer.parseInt(entry.getValue()));
					//追加
					buyerCart.addItem(item);
				}
			}
		}
		return buyerCart;
	}

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private DetailDao detailDao;
	//保存订单详情
	public void insertOrder(Order order, String username) {
//		订单表
//		ID、订单编号   由Redis生成
		Long id = jedis.incr("oid");
		order.setId(id);
		
		BuyerCart buyerCart = selectBuyerCartFromRedis(username);
		List<BuyerItem> items = buyerCart.getItems();
		for (BuyerItem item : items) {
			item.setSku(selectSkuById(item.getSku().getId()));
		}
//		运费       由购物车提供
		order.setDeliverFee(buyerCart.getFee());
//		总价
		order.setTotalPrice(buyerCart.getTotalPrice());
//		订单价
		order.setOrderPrice(buyerCart.getProductPrice());
//		支付方式    由页面传递
//		支付要求    
//		留言       
//		送货方式   略
//		电话确认   略
//		支付状态   :0到付1待付款,2已付款,3待退款,4退款成功,5退款失败
		if(order.getPaymentWay() == 1){
			order.setIsPaiy(0);
		}else{
			order.setIsPaiy(1);
		}
//		订单状态 0:提交订单 1:仓库配货 2:商品出库 3:等待收货 4:完成 5待退货 6已退货
		order.setOrderState(0);
//		时间：     由程序提供
		order.setCreateDate(new Date());
//		用户ID   由程序提供
		String buyerId = jedis.get(username);
		order.setBuyerId(Long.parseLong(buyerId));
		orderDao.insertSelective(order);
		
		//保存详情
		for (BuyerItem item : items) {
			Detail detail = new Detail();
//			订单详情表
//			Id   自增长
//			订单ID  
			detail.setOrderId(order.getId());
//			商品ID（编号）   由购物车中购物项
			detail.setProductId(item.getSku().getProductId());
//			商品名称
			detail.setProductName(item.getSku().getProduct().getName());
//			颜色名称
			detail.setColor(item.getSku().getColor().getName());
//			尺码名称
			detail.setSize(item.getSku().getSize());
//			价格
			detail.setPrice(item.getSku().getPrice());
//			数量、    快照
			detail.setAmount(item.getAmount());
			
			detailDao.insertSelective(detail);
		}
		//删除购物车中已经  已经提供城订单的商品
		jedis.del("buyerCart:" + username);
	}
}
