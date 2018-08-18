package cn.itcast;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.common.json.JsonUtils;
import cn.itcast.core.pojo.ad.Ad;

/**
 * 测试Json
 * @author xujie
 *
 */
public class TestJson {
	
	/*
	{
		"height":399,
		"title":"",
		"width":670,
		"src":"/images/5653e38eNed7f4ffc.jpg",
		"href":"javascript:;"
	},
	{"title":"转Json","url":"asdfas","picture":"/images/5653e38eNed7f4ffc.jpg","height":399,"width":670}
	 加上@JsonProperty(value="src")后
	{"title":"转Json","height":399,"width":670,"href":"asdfas","src":"/images/5653e38eNed7f4ffc.jpg"}
	*/
	@Test
	public void testObj2Json() throws Exception {
		Ad ad = new Ad();
		ad.setHeight(399);
		ad.setWidth(670);
		ad.setTitle("转Json");
		ad.setUrl("asdfas");
		ad.setPicture("/images/5653e38eNed7f4ffc.jpg");
		ObjectMapper om = new ObjectMapper();
		//设置不转null
		om.setSerializationInclusion(Include.NON_NULL);
		//序列化,读还是写
		String value = null;
		value = om.writeValueAsString(ad);
		
		System.out.println(value);
		
		//测试Json字符串转成POJO对象
		Ad ad2 = JsonUtils.jsonToObject(value, Ad.class);
		System.out.println(ad2);
	}
}
