package cn.itcast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

/**
 * 测试Jedis
 * @author xujie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestJedis {

	@Autowired
	private Jedis jedis;
	
	@Test
	public void testJedis() throws Exception {
		jedis.set("haha", "hehe");
		jedis.close();
//		Jedis jedis = new Jedis("192.168.200.128", 6379);
//		String pno = jedis.get("pno");
//		System.out.println(pno);
	}
}
