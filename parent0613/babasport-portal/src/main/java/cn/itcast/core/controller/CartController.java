package cn.itcast.core.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.common.json.JsonUtils;
import cn.itcast.common.utils.RequestUtils;
import cn.itcast.common.web.Constants;
import cn.itcast.core.pojo.BuyerCart;
import cn.itcast.core.pojo.BuyerItem;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.service.BuyerService;
import cn.itcast.core.service.SessionProvider;

/**
 * 购物车管理
 * 加入购物车
 * 去购物车结算
 * 结算
 * 提交订单
 * @author xujie
 *
 */
@Controller
public class CartController {

	@Autowired
	private SessionProvider sessionProvider;
	
	//加入购物车
	@RequestMapping(value="/shopping/buyerCart")
	public String buyerCart(Long skuId, Integer amount, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
//		ObjectMapper om = new ObjectMapper();
//		om.setSerializationInclusion(Include.NON_NULL);
		
		//未登录
		BuyerCart buyerCart = null;
		//1.获取Cookie
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies) {
				//2.如果Cookie是否有购物车
				if(Constants.BUYER_CART.equals(cookie.getName())){
					//有,将JSON字符串转成POJO对象(调用了方法)
					buyerCart = JsonUtils.jsonToObject(cookie.getValue(),BuyerCart.class);
					break;
				}
			}
		}
		//3.没有  创建购物车  对象中
		if(null == buyerCart){
			buyerCart = new BuyerCart();
		}
		//4.追加当前歀
		if(null != skuId && null != amount){
			Sku sku = new Sku();
			sku.setId(skuId);
			BuyerItem item = new BuyerItem();
			item.setSku(sku);
			item.setAmount(amount);
			//追加当前歀
			buyerCart.addItem(item);
		}
		//用户是否登陆
		String username = sessionProvider.getAttributeForUserName(RequestUtils.getCSESSION(request, response));
		if(null != username){
			//登陆了
			//5.将购物车追加到Redis中
			buyerService.insertBuyerCartToRedis(buyerCart, username);
			//6.清空Cookie中的购物车
			Cookie cookie = new Cookie(Constants.BUYER_CART, null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}else{
			//未登录
			if(null != skuId && null != amount){
				//5.创建Cookie  新购物车保存到Cookie中
				Cookie cookie = new Cookie(Constants.BUYER_CART, JsonUtils.ObjeceToJson(buyerCart));
				//存货时间
				cookie.setMaxAge(60*60*24);
				//设置路径
				cookie.setPath("/");
				//6.写回浏览器
				response.addCookie(cookie);
			}
		}
		//7.重定向
		return "redirect:/shopping/toCart";
	}
	
	@Autowired
	private BuyerService buyerService;
	//去购物车结算
	@RequestMapping(value="/shopping/toCart")
	public String toCart(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		//未登录
		BuyerCart buyerCart = null;
		//1.获取Cookie
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies) {
				//2.获取Cookie中购物车
				if(Constants.BUYER_CART.equals(cookie.getName())){
					//有
					buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
					break;
				}
			}
		}
		//用户是否登陆
		String username = sessionProvider.getAttributeForUserName(RequestUtils.getCSESSION(request, response));
		if(null != username){
			//登陆了
			//3.有 将购物车保存到Redis中 清空Cookie
			if(null != buyerCart){
				buyerService.insertBuyerCartToRedis(buyerCart, username);
				Cookie cookie = new Cookie(Constants.BUYER_CART, null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			//5.获取Redis中的购物车(所有购物车)
			buyerCart = buyerService.selectBuyerCartFromRedis(username);
		}
		//4.没有
		//5.将购物车装满
		if(null != buyerCart){
			List<BuyerItem> items = buyerCart.getItems();
			if(items.size() > 0){
				//通过SkuId 查询sku对象 (里面查询颜色对象 商品对象)
				for (BuyerItem item : items) {
					item.setSku(buyerService.selectSkuById(item.getSku().getId()));
				}
			}
		}
		//6.跳转都购物车页面进行回显
		model.addAttribute("buyerCart", buyerCart);
		return "cart";
	}
	
	//结算  提交订单  个人资料  会员中心    用户必须登陆了
	@RequestMapping(value = "/buyer/trueBuy")
	public String trueBuy(Long[] ids, HttpServletRequest request, HttpServletResponse response, Model model){
		//1.判断用户是否登陆  1)未登录 跳转到登陆页面  重定向到首页  2)登陆 放行---写在拦截器中
		//2.判断购物车中是否有商品  1)无商品  刷新购物车页面进行提示  2)有商品  继续判断
		String username = sessionProvider.getAttributeForUserName(RequestUtils.getCSESSION(request, response));
		BuyerCart buyerCart = buyerService.selectBuyerCartFromRedis(username);
		if(null != buyerCart){
			List<BuyerItem> items = buyerCart.getItems();
			if(items.size() > 0){
				//有商品
				//3.判断购物车中商品是否有货  1)无货  刷新购物车页面进行无货提示  2)有货  真过了进入订单提交页面
				Boolean flag = false;
				//注意：购买数量大于库存数量视为无货
				for (BuyerItem item : items) {
					//装满
					item.setSku(buyerService.selectSkuById(item.getSku().getId()));
					//判断是否有货
					if(item.getAmount() > item.getSku().getStock()){
						//无货
						item.setIsHave(false);
						flag = true;
					}
				}
				//有一个无货都不让过
				if(flag){
					//存在无货的商品
					model.addAttribute("buyerCart", buyerCart);
					return "cart";
				}
			}else{
				//没有商品
				return "redirect:shopping/toCart";
			}
		}else{
			//没有购物车
			return "redirect:shopping/toCart";
		}
		//正常
		return "order";
	}
	
	//提交订单
	@RequestMapping(value="/buyer/submitOrder")
	public String submitOrder(Order order, HttpServletRequest request, HttpServletResponse response){
		String username = sessionProvider.getAttributeForUserName(RequestUtils.getCSESSION(request, response));
		//保存订单  订单详情
		buyerService.insertOrder(order, username);
		//Model 回显
		return "success";
	}
	
}