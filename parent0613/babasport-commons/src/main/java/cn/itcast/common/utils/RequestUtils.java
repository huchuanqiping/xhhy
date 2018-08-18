package cn.itcast.common.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 生成令牌
 * @author xujie
 *
 */
public class RequestUtils {

	//生成令牌
	public static String getCSESSION(HttpServletRequest request, HttpServletResponse response){
		//获取Cookie
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies) {
				//获取Cookie中的令牌
				if("CSESSIONID".equals(cookie.getName())){
					//有  直接返回此令牌
					return cookie.getValue();
				}
			}
		}
		//没有  创建令牌
		String csessionid = UUID.randomUUID().toString().replaceAll("-", "");
		//创建Coolie 并保存令牌到Cookie中 把Cookie写回浏览器中
		Cookie cookie = new Cookie("CSESSIONID",csessionid);
		//关闭浏览器时 挂掉 0:立即销毁  -1:关闭浏览器时销毁   >0：60*60:一小时
		cookie.setMaxAge(-1);
		//设置路径
		cookie.setPath("/");
		//写回浏览器
		response.addCookie(cookie);
		//直接使用创建的令牌
		return csessionid;
	}
}
