package cn.itcast.common.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 对象转换JSON
 * @author xujie
 *
 */
public class JsonUtils {

	//List集合转成JSON字符串
	public static String ObjeceToJson(Object obj) {
		ObjectMapper om = new ObjectMapper();
		//设置null不转
		om.setSerializationInclusion(Include.NON_NULL);
		//写
		String value = null;
		try {
			value = om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	//将JSON字符串转成POJO对象
	public static <T> T jsonToObject(String json, Class<T> t){
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		T object=null;
		try {
			object = om.readValue(json,t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return object;
	}
}