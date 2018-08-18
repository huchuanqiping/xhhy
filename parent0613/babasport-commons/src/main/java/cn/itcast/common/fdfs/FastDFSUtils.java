package cn.itcast.common.fdfs;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * 上传图片FastDFS
 * @author xujie
 *
 */
public class FastDFSUtils {

	//上传图片  返回路径
	public static String uploadPic(byte[] pic, String name, long size) throws Exception{
		//类 Spring公司提供  专门读取classpath的资源文件
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		//全局配置IP  读取IP文件
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		//连接跟踪器
		TrackerClient trackerClient = new TrackerClient();
		//连接  此对象中封闭了(Stoage的IP地址
		TrackerServer trackerServer = trackerClient.getConnection();
		//连接存储节点
		StorageServer storageServer = null;
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
		//上传图片
		String ext = FilenameUtils.getExtension(name);
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("filename",name);
		meta_list[1] = new NameValuePair("fileext",ext);
		meta_list[2] = new NameValuePair("filesize",String.valueOf(size));
		
		String path = storageClient1.upload_file1(pic, ext, meta_list);
		return path;
	}
}
