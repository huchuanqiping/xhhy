package cn.itcast.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理中心
 * @author xujie
 *
 */
@Controller
@RequestMapping(value="/control/")
public class CenterController {

	//后台入口
	@RequestMapping(value="index.do")
	public String index() {
		return "index";
	}
	
	//头
	@RequestMapping(value="top.do")
	public String top() {
		return "top";
	}
	
	//身体
	@RequestMapping(value="main.do")
	public String main() {
		return "main";
	}
	
	//身体--左
	@RequestMapping(value="left.do")
	public String left() {
		return "left";
	}
	
	//身体--右
	@RequestMapping(value="right.do")
	public String right() {
		return "right";
	}
	
	//商品身体
	@RequestMapping(value="frame/product_main.do")
	public String product_main() {
		return "frame/product_main";
	}
	
	//广告身体
	@RequestMapping(value="frame/ad_main.do")
	public String ad_main() {
		return "frame/ad_main";
	}
	
	//商品身体--左
	@RequestMapping(value="frame/product_left.do")
	public String product_left() {
		return "frame/product_left";
	}
	
	//广告身体--左
	@RequestMapping(value="frame/ad_left.do")
	public String ad_left() {
		return "frame/ad_left";
	}
	
	
	
	
	/*@Autowired
	private TestTbService testTbService;
	
	//测试
	@RequestMapping(value="/test/index.do")
	public String index(){
		TestTb testTb = new TestTb();
		testTb.setName("林心如");
		testTb.setBirthday(new Date());
		testTbService.insertTestTb(testTb);
		return "index";
	}*/
}
