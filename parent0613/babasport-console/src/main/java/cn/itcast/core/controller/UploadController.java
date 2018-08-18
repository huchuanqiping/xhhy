package cn.itcast.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.itcast.common.web.Constants;
import cn.itcast.core.service.product.UploadService;

/**
 * 上传图片
 * @author xujie
 *
 */
@Controller
public class UploadController {

	@Autowired
	private UploadService uploadService;
	
	//上传图片
	@RequestMapping(value="/upload/uploadPic.do")
	public void uploadPic(MultipartFile pic, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//保存图片到FastDFS上去
		String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		
		//生成图片名  时间  格式生成  精准到毫秒  + 三位随机数(自己写)
//		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String format = df.format(new Date());
//		Random random = new Random();
//		for (int i = 0; i < 3; i++) {
//			format += random.nextInt(10);
//		}
		//保存图片
//		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
//		String realPath = request.getSession().getServletContext().getRealPath("");
//		String path = "/upload/"+format +"."+ext;
//		String url = realPath + path;
//		System.out.println(url);
		//保存成功
//		pic.transferTo(new File(url));
		//回显图片的路径 <img src=path
		
		JSONObject jo = new JSONObject();
		jo.put("path",Constants.IMG_URL+path);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jo.toString());
	}
	
	//上传多张图片
	@RequestMapping("/upload/uploadPics.do")
	public @ResponseBody
	List<String> uploadPics(@RequestParam(required = false) MultipartFile[] pics) throws Exception{
		List<String> urls = new ArrayList<String>();
		//遍历数组
		for (MultipartFile pic : pics) {
			//保存图片到FastDFS上去
			String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			String url = Constants.IMG_URL+path;
			urls.add(url);
		}
		//转json
		return urls;
	}
	
	//KindEditor上传图片
	@RequestMapping(value="/upload/uploadFck")
	public void uploadFck(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//无敌版接收
		MultipartRequest mr = (MultipartRequest) request;
		//只有图片或文件  支持多张图片
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		Set<Entry<String,MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile pic = entry.getValue();
			//保存图片到FastDFS上去
			String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			String url = Constants.IMG_URL+path;
			///回显
			JSONObject jo = new JSONObject();
			jo.put("url", url);
			jo.put("error", 0);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jo.toString());
		}
	}
}
