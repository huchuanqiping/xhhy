package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;

public interface BrandService {

	//通过name 是否可见  查询结果集
	public List<Brand> selectBrandListByQuery(String name, Integer isDisplay);
	
	//通过name 是否可见  查询分页对象
	public Pagination selectPaginationByQuery(Integer pageNo, String name, Integer isDisplay);
	
	//通过ID查询品牌
	public Brand selectBrandById(Long id);
	
	//修改
	public void updateBrandById(Brand brand);

	//删除  批量
	public void deletes(Long[] ids);
}
