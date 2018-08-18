package cn.itcast.core.service.product;

import org.springframework.ui.Model;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Product;

public interface ProductService {

	//商品列表
	public Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow, Model model);
	
	//保存商品表  保存库存表
	public void insertProduct(Product product);

	//上架
	public void isShow(Long[] ids);
}
