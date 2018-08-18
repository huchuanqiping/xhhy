package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrderService;

/**
 * 订单列表
 * 订单查询
 * 订单删除
 * 收货地址管理
 * @author xujie
 *
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	//去订单页面
	@RequestMapping(value="/order/myrder")
	public String myOrder(Model model){
		
		List<Order> orders = orderService.selectOrders();
		model.addAttribute("orders", orders);
		return "my-orders";
	}
}
