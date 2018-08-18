package cn.itcast.core.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.common.utils.RequestUtils;
import cn.itcast.core.pojo.user.Buyer;
import cn.itcast.core.service.BuyerService;
import cn.itcast.core.service.SessionProvider;

/**
 * 单点登陆管理
 * @author xujie
 *
 */
@Controller
public class LoginController {

	//去登陆页面
	@RequestMapping(value = "/login.aspx",method = RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private SessionProvider sessionProvider;
	//提交登陆表单
	@RequestMapping(value = "/login.aspx",method = RequestMethod.POST)
	public String login(String username, String password, String ReturnUrl, 
			HttpServletRequest request, HttpServletResponse response, Model model){
		
		//判断  验证码不能为空
		//验证码必须正确(equalsIgnoreCase)  不能超时
		//判断用户名不能为空
		if(null != username){
			//判断密码不能为空
			if(null != password){
				//判断用户名是否存在
				//从数据库查
				Buyer buyer = buyerService.selectBuyerByUsername(username);
				if(null != buyer){
					//判断密码是否正确
					if(buyer.getPassword().equals(encodePassword(password))){
						//将用户名保存到Session中
						sessionProvider.setAttributeForUserName
						(RequestUtils.getCSESSION(request, response), username);
						
						//request.getSession()
						//1:获取Request中的Cookie
						//2:从Cookie中取出JSESSIONID 的value值  32长度 UUID36位长度去掉四个小横线
						//3:通过32位的长度字符串  获取Session对象
						//request.getSession()===你自己的Session Tomcat 许多 Session 当前在线100人,就有100个Session
						
						//返回之前访问的页面
						return "redirect:" + ReturnUrl;
					}else{
						model.addAttribute("error", "密码不正确");
					}
				}else{
					model.addAttribute("error", "用户名不存在");
				}
			}else{
				model.addAttribute("error", "密码不能为空");
			}
		}else{
			model.addAttribute("error", "用户名不能为空");
		}
		//进入Login登陆提示
		return "login";
	}
	
	//加密
	public String encodePassword(String password){
		//工具类或者spring等加密工具,底层都是用JDK
		String algorithm = "MD5";
		//加盐
//		password = "asdfadgjcnvkaernlkdfgblkadshjkuoigjnfm";
		
		char[] encodeHex = null;
		try {
			MessageDigest instance = MessageDigest.getInstance(algorithm);
			byte[] digest = instance.digest(password.getBytes());
			//十六进制  加载
			encodeHex = Hex.encodeHex(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(encodeHex);
	}
	
	public static void main(String[] args) {
		LoginController l = new LoginController();
		String p = l.encodePassword("123456");
		System.out.println(p);
	}
	
	//判断是否登陆
	@RequestMapping(value="/isLogin.aspx")
	public @ResponseBody
	MappingJacksonValue isLogin(String callback, HttpServletRequest request, HttpServletResponse response){
		Integer result = 0;
		//判断用户名在不在
		String username = sessionProvider.getAttributeForUserName(RequestUtils.getCSESSION(request, response));
		if(null != username){
			//登陆了
			result = 1;
		}
		MappingJacksonValue mjv = new MappingJacksonValue(result);
		//设置门牌号callback
		mjv.setJsonpFunction(callback);
		return mjv;
		
	/*	JSONObject jo = new JSONObject();
		jo.put("result", result);
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
