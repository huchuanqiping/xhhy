package cn.itcast.core.dao.product;

import java.util.List;

import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.BrandQuery;

/**
 * 品牌Dao
 * @author xujie
 *
 */
public interface BrandDao {

	//通过name 是否可见  查询结果集
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery);
	
	//查询符合条件的总条数
	public Integer selectCount(BrandQuery brandQuery);
	
	//通过ID查询品牌
	public Brand selectBrandById(Long id);
	
	//修改
	public void updateBrandById(Brand brand);

	//删除  批量
	public void deletes(Long[] ids);
}
