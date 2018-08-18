package cn.itcast.common.conversion;

import org.springframework.core.convert.converter.Converter;

/**
 * 去掉前后空格转换
 * @author xujie
 *
 */
public class TrimConverter implements Converter<String, String>{

	@Override
	public String convert(String source) {
		try {
			//"   "  ""
			if(null != source){
				source = source.trim();
				//判断不为空
				if(!"".equals(source)){
					return source;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	
}
