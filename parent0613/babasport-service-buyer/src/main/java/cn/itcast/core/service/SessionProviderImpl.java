package cn.itcast.core.service;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

/**
 * 远程Session  本类Session提供类
 * @author xujie
 *
 */
public class SessionProviderImpl implements SessionProvider{

	@Autowired
	private Jedis jedis;
	//分钟
	private Integer exp = 30;
	public void setExp(Integer exp){
		this.exp = exp;
	}
	//保存用户名到Redis远程Session中
	//key  ==  jsessionid
	public void setAttributeForUserName(String key, String value){
		jedis.set(key + ":USER_NAME", value);
		//存货30分钟
		jedis.expire(key + ":USER_NAME", 60*exp);
	}
	
	//获取用户名从Redis
	public String getAttributeForUserName(String key){
		String value = jedis.get(key + ":USER_NAME");
		if(null != value){
			//存货30分钟
			jedis.expire(key + ":USER_NAME", 60*exp);
		}
		return value;
	}
	//保存验证码到Redis中
	//获取验证码到Redis中
	//清除Redis中的Session
}
