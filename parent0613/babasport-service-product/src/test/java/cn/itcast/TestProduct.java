package cn.itcast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.pojo.product.Product;

/**
 * 本次测试采用注解式开发
 * @author xujie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestProduct {

	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testname() throws Exception {
		Product p = productDao.selectByPrimaryKey(1L);
		System.out.println(p);
	}
}
