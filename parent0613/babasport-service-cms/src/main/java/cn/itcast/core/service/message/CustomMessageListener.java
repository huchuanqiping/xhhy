package cn.itcast.core.service.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.service.CmsService;
import cn.itcast.core.service.StaticPageService;

/**
 * 自定义处理类
 * @author xujie
 *
 */
public class CustomMessageListener implements MessageListener {

	@Autowired
	private CmsService cmsService;
	@Autowired
	private StaticPageService staticPageService;
	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage amt = (ActiveMQTextMessage) message;
		try {
			String id = amt.getText();
			System.out.println("CMS:"+id);
			
			//TODO 将商品页面进行静态化处理
			Map<String,Object> root = new HashMap<>();
			
			//通过商品ID 查询商品对象
			Product p = cmsService.selectProductById(Long.parseLong(id));
			root.put("product", p);
			//通过商品ID并且库存大于0 查询Sku对象(结果集) Sku里面颜色对象
			List<Sku> skus = cmsService.selectSkuListByProductId(Long.parseLong(id));
			//去掉重复颜色
			Set<Color> colors = new HashSet<Color>();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			root.put("colors", colors);
			root.put("skus", skus);
			//执行静态化
			staticPageService.index(root, Long.parseLong(id));
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
