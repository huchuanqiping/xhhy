package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.common.json.JsonUtils;
import cn.itcast.core.dao.ad.AdDao;
import cn.itcast.core.dao.ad.PositionDao;
import cn.itcast.core.pojo.ad.Ad;
import cn.itcast.core.pojo.ad.AdQuery;
import cn.itcast.core.pojo.ad.Position;
import cn.itcast.core.pojo.ad.PositionQuery;
import redis.clients.jedis.Jedis;

/**
 * 广告管理
 * 广告位置的树状
 * @author xujie
 *
 */
@Service("adService")
public class AdServiceImpl implements AdService{

	@Autowired
	private PositionDao positionDao;
	@Autowired
	private AdDao adDao;
	
	//通过位置父ID 查询广告位置
	public List<Position> selectPositionListByParentId(Long ParentId){
		PositionQuery positionQuery = new PositionQuery();
		positionQuery.createCriteria().andParentIdEqualTo(ParentId);
		return positionDao.selectByExample(positionQuery);
	}
	
	//通过广告位置ID 查询所有广告
	public List<Ad> selectAdListByPositionId(Long positionId){
		AdQuery adQuery = new AdQuery();
		adQuery.createCriteria().andPositionIdEqualTo(positionId);
		List<Ad> ads = adDao.selectByExample(adQuery);
		//直接查询位置
		Position position = positionDao.selectByPrimaryKey(positionId);
		for (Ad ad : ads) {
			ad.setPosition(position);
		}
		return ads;
	}

	//通过广告位置ID 查询位置标题
	public Position selectPositionById(Long positionId) {
		return positionDao.selectByPrimaryKey(positionId);
	}
	
	//添加
	public void inserAd(Ad ad){
		adDao.insertSelective(ad);
	}
	
	@Autowired
	private Jedis jedis;
	
	//加载大广告位置的所以广告
	public String selectAdList(Long positionId){
		//根据广告位的ID 从缓存中查询广告结果集的JSON字符串
		String adjson = jedis.get("ads:" + positionId);
		//判断缓存是否有广告  如果没有去数据库查询,再将查询到的广告结果集转成JSON字符串保存到缓存中
		if(null == adjson){
			AdQuery adQuery = new AdQuery();
			adQuery.createCriteria().andPositionIdEqualTo(positionId);
			List<Ad> ads = adDao.selectByExample(adQuery);
			//List转JSON字符串
			adjson = JsonUtils.ObjeceToJson(ads);
			jedis.set("ads:" + positionId, adjson);
		}
		return adjson;
	}
}