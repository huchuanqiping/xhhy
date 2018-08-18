package cn.itcast.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.service.product.BrandService;

/**
 * 品牌管理
 * 列表查询
 * 去修改
 * 修改
 * @author xujie
 *
 */
@Controller
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	
	//品牌管理按钮
	@RequestMapping(value="/brand/list.do")
	public String list(Integer pageNo, String name, Integer isDisplay, Model model){
		
		//通过name 是否可见  查询结果集
//		List<Brand> brands = brandService.selectBrandListByQuery(name, isDisplay);
//		model.addAttribute("brands", brands);
		//返回分页对象
		Pagination pagination = brandService.selectPaginationByQuery(pageNo, name, isDisplay);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);
		return "brand/list";
	}
	
	//去修改页面
	@RequestMapping(value="/brand/toEdit.do")
	public String toEdit(Long id, Model model){
		Brand brand = brandService.selectBrandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	
	//提交修改
	@RequestMapping("/brand/edit.do")
	public String edit(Brand brand){
		brandService.updateBrandById(brand);
		//跳转视图:重定向
		return "redirect:/brand/list.do";
	}
	
	//提交修改
	@RequestMapping("brand/delete.do")
	public String deletes(Long[] ids, String name, Integer isDisplay, Integer pageNo, Model model){
		brandService.deletes(ids);
		model.addAttribute("name",name);
		model.addAttribute("isDisplay",isDisplay);
		model.addAttribute("pageNo", pageNo);
		//跳转视图:重定向,能够带参数重定向到list方法
		//如果不想绑定参数可用内部转发的方法,走同一个request,自然带了之前的参数,即可不用model:
		//return "reward:/brand/list.do";
		return "redirect:/brand/list.do";
	}
}
