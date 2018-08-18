package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.service.product.BrandService;
import cn.itcast.core.service.product.ColorService;
import cn.itcast.core.service.product.ProductService;

/**
 * 商品管理
 * 商品列表
 * 商品添加
 * 商品上架
 * @author xujie
 *
 */
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ColorService colorService;
	
	//商品列表查询
	@RequestMapping(value = "/product/list.do")
	public String list(Integer pageNo, String name, Long brandId, Boolean isShow, Model model){
		//查询品牌结果集
		List<Brand> brands = brandService.selectBrandListByQuery(null, 1);
		model.addAttribute("brands", brands);
		Pagination pagination = productService.selectPaginationByQuery(pageNo, name, brandId, isShow, model);
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
		return "product/list";
	}
	
	//去商品的添加页面
	@RequestMapping(value = "/product/toAdd.do")
	public String toAdd(Model model){
		//默认值
		//品牌结果集
		List<Brand> brands = brandService.selectBrandListByQuery(null, 1);
		model.addAttribute("brands", brands);
		//颜色结果集
		List<Color> colors = colorService.selectColorList();
		model.addAttribute("colors", colors);
		return "product/add";
	}
	
	//商品的添加
	@RequestMapping(value = "/product/add.do")
	public String add(Product product){
		//Service层  保存商品
		productService.insertProduct(product);
		return "redirect:/product/list.do";
	}
	
	//上架
	@RequestMapping(value = "/brand/isShow.do")
	public String isShow(Long[] ids, Model model){
		//上架
		productService.isShow(ids);
		return "forward:/product/list.do";
	}
}
