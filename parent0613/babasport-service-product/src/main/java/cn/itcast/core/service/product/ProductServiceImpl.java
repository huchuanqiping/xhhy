package cn.itcast.core.service.product;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.ProductQuery;
import cn.itcast.core.pojo.product.ProductQuery.Criteria;
import cn.itcast.core.pojo.product.Sku;
import redis.clients.jedis.Jedis;
@Service("productService")
@Controller
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	//返回分页对象
	public Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow, Model model) {
		//创建条件对象
		ProductQuery productQuery = new ProductQuery();
		//当前页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//每页数
		productQuery.setPageSize(5);
		//排序
		productQuery.setOrderByClause("id desc");
		//装条件的对象
		Criteria createCriteria = productQuery.createCriteria();
		StringBuilder params = new StringBuilder();
		//条件判断
		if(null != name){
			createCriteria.andNameLike("%"+name+"%");
			params.append("name="+name);
		}
		if(null != brandId){
			createCriteria.andBrandIdEqualTo(brandId);
			params.append("&brandId="+brandId);
		}
		if(null != isShow){
			createCriteria.andIsShowEqualTo(isShow);
			params.append("&isShow="+isShow);
		}else{
			//默认下架
			createCriteria.andIsShowEqualTo(false);
			params.append("&isShow="+false);
		}
		Pagination pagination = new Pagination(
				productQuery.getPageNo(),
				productQuery.getPageSize(),
				productDao.countByExample(productQuery)
				);
		//查询结果集  limit分页
		pagination.setList(productDao.selectByExample(productQuery));
		//分页在页面上显示
		String url = "/product/list.do";
		pagination.pageView(url, params.toString());
		return pagination;
	}

	@Autowired
	private SkuDao skuDao;
	@Autowired
	private Jedis jedis;
	//保存商品表  保存库存表
	public void insertProduct(Product product){
		//Redis生成商品ID
		Long id = jedis.incr("pno");
		product.setId(id);
		//下架
		product.setIsShow(false);
		//不删除
		product.setIsDel(true);
		//添加删除
		product.setCreateTime(new Date());
		//保存商品时,返回商品ID,在productDao.xml中加入useGeneratedKeys="true" keyProperty="id"即可
		//insert into bbs_product (id,name,imgUrl,....) values (null,'asfsafd',null,'safsa',null)  没有进行判断
		//insert into bbs_product (name,....) values ('asfsafd','safsa') 保存到数据库样子 一样的
		productDao.insertSelective(product);
		
		//保存Sku  库存表数据
		//遍历颜色
		for (String color : product.getColors().split(",")) {
			//遍历尺寸
			for (String size : product.getSizes().split(",")) {
				Sku sku = new Sku();
				//商品ID
				sku.setProductId(product.getId());
				//颜色ID
				sku.setColorId(Long.parseLong(color));
				//尺码
				sku.setSize(size);
				//市场价
				sku.setMarketPrice(0f);
				//售价
				sku.setPrice(0f);
				//运费
				sku.setDeliveFee(10f);
				//购买限制
				sku.setUpperLimit(200);
				//库存
				sku.setStock(0);
				//时间
				sku.setCreateTime(new Date());
				
				//保存
				skuDao.insertSelective(sku);
			}
		}
	}

	@Autowired
	private JmsTemplate jmsTemplate;
	//上架
	public void isShow(Long[] ids) {
		Product product = new Product();
		//上架状态
		product.setIsShow(true);
		for (final Long id : ids) {
			//ID
			product.setId(id);
			//更改商品状态,即执行上架
			productDao.updateByPrimaryKeySelective(product);
			
			//发送消息(MQ应用)
			jmsTemplate.send(new MessageCreator(){

				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(String.valueOf(id));
				}
			});
			
		}
		
	}
}