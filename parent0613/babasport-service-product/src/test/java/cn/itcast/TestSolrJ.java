package cn.itcast;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试SolrJ
 * @author xujie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestSolrJ {

	@Autowired
	private SolrServer solrServer;
	
	@Test
	public void testSolrJ() throws Exception {
//		String baseUrl = "http://192.168.200.128:8080/solr";
//		SolrServer solrServer = new HttpSolrServer(baseUrl);
		
		//保存
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", 2);
		doc.setField("name", "貂皮");
		solrServer.add(doc);
		solrServer.commit();
	}
}
