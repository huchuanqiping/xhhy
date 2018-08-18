package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.pojo.order.Order;

public interface OrderService {

	//获取订单列表
	public List<Order> selectOrders();

}
