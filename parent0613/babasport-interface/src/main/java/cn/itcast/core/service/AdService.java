package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.pojo.ad.Ad;
import cn.itcast.core.pojo.ad.Position;

public interface AdService {

	//通过位置父ID 查询广告位置
	public List<Position> selectPositionListByParentId(Long ParentId);
	
	//通过广告位置ID 查询所有广告
	public List<Ad> selectAdListByPositionId(Long positionId);

	//通过广告位置ID 查询位置标题
	public Position selectPositionById(Long positionId);
	
	//添加
	public void inserAd(Ad ad);
	
	//加载大广告位置的所以广告
	public String selectAdList(Long positionId);
}
