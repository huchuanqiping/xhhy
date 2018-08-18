package cn.itcast.core.service.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.service.SolrService;

/**
 * 自定义处理类
 * @author xujie
 *
 */
public class CustomMessageListener implements MessageListener {

	@Autowired
	private SolrService solrService;
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ActiveMQTextMessage amt = (ActiveMQTextMessage) message;
		try {
			String id = amt.getText();
			System.out.println("solr:"+id);
			solrService.insertProductToSolr(Long.parseLong(id));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
