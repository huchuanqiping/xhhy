package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderQuery;
/**
 * 订单列表
 * 订单查询
 * 订单删除
 * 收货地址管理
 * @author xujie
 *
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDao orderDao;
	//获取订单列表
	public List<Order> selectOrders() {
		OrderQuery orderQuery = new OrderQuery();
		List<Order> list = orderDao.selectByExample(orderQuery);
		return list;
	}
}
