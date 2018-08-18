package cn.itcast.core.service;

public interface SessionProvider {

	//保存用户名到Redis远程Session中
	public void setAttributeForUserName(String key, String value);
	
	//获取用户名从redis
	public String getAttributeForUserName(String key);
}
