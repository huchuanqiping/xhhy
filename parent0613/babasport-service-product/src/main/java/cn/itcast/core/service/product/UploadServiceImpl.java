package cn.itcast.core.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.fdfs.FastDFSUtils;
/**
 * 上传图片
 * @author xujie
 *
 */
@Service("uploadService")
@Transactional
public class UploadServiceImpl implements UploadService{

	//上传图片
	public String uploadPic(byte[] pic, String name, long size) throws Exception{
		return FastDFSUtils.uploadPic(pic, name, size);
	}
}
