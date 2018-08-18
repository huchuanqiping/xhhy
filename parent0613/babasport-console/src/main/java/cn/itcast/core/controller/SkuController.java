package cn.itcast.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.service.product.SkuService;

/**
 * 库存管理
 * 去库存管理页面
 * 修改
 * 保存
 * @author xujie
 *
 */
@Controller
public class SkuController {

	@Autowired
	private SkuService skuService;
	
	//去库存页面
	@RequestMapping(value = "/sku/list.do")
	public String toSku(Long productId, Model model){
		//通过商品ID 查询Sku结果集（每一个SKu里颜色对象）
		List<Sku> skus = skuService.selectSkuListByProductId(productId);
		model.addAttribute("skus", skus);
		return "sku/list";
	}
	
	//保存
	//修改
	@RequestMapping(value = "/sku/addSku.do")
	public void addSku(Sku sku, HttpServletResponse response){
		JSONObject jo = new JSONObject();
		try {
			skuService.updateSkuById(sku);
			jo.put("message", "保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jo.put("message", "保存失败");
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
