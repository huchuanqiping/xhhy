package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.ad.Position;
import cn.itcast.core.service.AdService;

/**
 * 广告位置管理
 * 广告树状加载
 * 广告位置添加
 * 广告位置的修改
 * 删除
 * @author xujie
 *
 */
@Controller
public class PositionController {
	
	@Autowired
	private AdService adService;
	//广告树状加载
	//异步请求加载页面
	@RequestMapping(value="/position/tree.do")
	public String tree(String root, Model model){
		//判断
		if("source".equals(root)){
			//初始化加载  顶级元素
			List<Position> positions = adService.selectPositionListByParentId(0L);
			model.addAttribute("list", positions);
		}else{
			//加载非顶级元素
			List<Position> positions = adService.selectPositionListByParentId(Long.parseLong(root));
			model.addAttribute("list", positions);
		}
		return "position/tree";
	}
	
	//加载父的子广告位置
	@RequestMapping(value="/position/list.do")
	public String list(Long root, Model model){
		//加载非顶级元素
		List<Position> positions = adService.selectPositionListByParentId(root);
		model.addAttribute("positions", positions);
		return "position/list";
	}
}
