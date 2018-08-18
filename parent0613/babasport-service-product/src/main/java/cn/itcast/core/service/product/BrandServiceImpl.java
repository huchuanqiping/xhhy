package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.common.web.Constants;
import cn.itcast.core.dao.product.BrandDao;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.BrandQuery;
import redis.clients.jedis.Jedis;
/**
 * 品牌
 * @author xujie
 *
 */
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandDao brandDao;
	
	//通过name 是否可见  查询结果集
	public List<Brand> selectBrandListByQuery(String name, Integer isDisplay){
		BrandQuery brandQuery = new BrandQuery();
		//判断
		if(null != name){
			brandQuery.setName(name);
		}
		if(null != isDisplay){
			brandQuery.setIsDisplay(isDisplay);
		}else{
			//默认是
			brandQuery.setIsDisplay(Constants.IS_DISPLAY);
		}
		return brandDao.selectBrandListByQuery(brandQuery);
	}
	
	//通过name 是否可见  查询分页对象
	public Pagination selectPaginationByQuery(Integer pageNo, String name, Integer isDisplay){
		BrandQuery brandQuery = new BrandQuery();
		//创建拼接条件
		StringBuilder params = new StringBuilder();
		//判断
		if(null != name){
			brandQuery.setName(name);
			params.append("name=").append(name);
		}
		if(null != isDisplay){
			brandQuery.setIsDisplay(isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}else{
			//默认是
			brandQuery.setIsDisplay(Constants.IS_DISPLAY);
			params.append("&isDisplay=").append(Constants.IS_DISPLAY);
		}
		//设置当前页给Query对象
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		//设置每页数
		brandQuery.setPageSize(5);
		//创建分页对象
		Pagination pagination = new Pagination(
				brandQuery.getPageNo(),
				brandQuery.getPageSize(),
				brandDao.selectCount(brandQuery)
				);
		//结果集
		pagination.setList(brandDao.selectBrandListByQuery(brandQuery));
		
		//页面分页展示 List<String> pageView = null
		//<a href="/brand/list.do?&isDisplay=0&pageNo=2">2</a>
		String url = "/brand/list.do";
		pagination.pageView(url, params.toString());
		return pagination;
	}

	//通过ID查询品牌
	public Brand selectBrandById(Long id){
		return brandDao.selectBrandById(id);
	}
	
	@Autowired
	private Jedis jedis;
	
	//修改  事务问题 切面 第一次去向Mysql数据发送BeginTrandsation  引擎 不支持数据 Spring管理事务  第一次Mysql
	public void updateBrandById(Brand brand){
		brandDao.updateBrandById(brand);//出问题  第二次
		//redis set 保存
		jedis.hset("brand", String.valueOf(brand.getId()), String.valueOf(brand.getName()));//redis有数据
	}
	//切面 callback 正常呢commit  第三次 Mysql
	
	//删除  批量
	public void deletes(Long[] ids) {
		brandDao.deletes(ids);
	}
}
