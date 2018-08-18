package cn.itcast.core.service;

import java.util.Map;

public interface StaticPageService {

	//静态化执行程序
	public void index(Map<String,Object> root, Long id);
}
