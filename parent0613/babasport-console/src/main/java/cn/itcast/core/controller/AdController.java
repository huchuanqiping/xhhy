package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.ad.Ad;
import cn.itcast.core.pojo.ad.Position;
import cn.itcast.core.service.AdService;

/**
 * 广告管理
 * 广告列表
 * 广告添加
 * @author xujie
 *
 */
@Controller
public class AdController {

	@Autowired
	private AdService adService;
	
	//广告列表
	@RequestMapping(value="/ad/list.do")
	public String list(Long root, Model model){
		//通过广告位置ID 查询所有广告
		List<Ad> ads = adService.selectAdListByPositionId(root);
		model.addAttribute("ads", ads);
		model.addAttribute("positionId", root);
		return "ad/list";
	}
	
	//去添加广告
	@RequestMapping(value="/ad/toAdd.do")
	public String toAdd(Long positionId, Model model){
		Position position = adService.selectPositionById(positionId);
		model.addAttribute("position", position);
		return "ad/add";
	}
	
	//添加广告
	@RequestMapping(value="/ad/add.do")
	public String add(Ad ad){
		adService.inserAd(ad);
		return "redirect:/ad/list.do?root=" + ad.getPositionId();
	}
	
}
