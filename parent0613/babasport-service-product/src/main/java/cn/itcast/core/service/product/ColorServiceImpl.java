package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.ColorQuery;
/**
 * 颜色管理
 * 查询颜色结果集
 * @author xujie
 *
 */
@Service("colorService")
@Transactional
public class ColorServiceImpl implements ColorService{

	@Autowired
	private ColorDao colorDao;
	
	//查询颜色结果集
	public List<Color> selectColorList() {
		ColorQuery colorQuery = new ColorQuery();
		colorQuery.createCriteria().andParentIdNotEqualTo(0L);
		return colorDao.selectByExample(colorQuery);
	}
}
