package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.pojo.product.Color;

public interface ColorService {

	//查询颜色结果集
	public List<Color> selectColorList();
}
