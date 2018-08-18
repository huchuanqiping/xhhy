package cn.itcast.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 静态化
 * @author xujie
 *
 */
public class StaticPageServiceImpl implements StaticPageService, ServletContextAware{

	//注入
	private Configuration conf;
	//声明
//	private FreeMarkerConfigurer freeMarkerConfigurer;
//	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
//		this.freeMarkerConfigurer = freeMarkerConfigurer;
//	}
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}

	//静态化执行程序
	public void index(Map<String,Object> root, Long id){
		//D:\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\babasport-portal
		//html/product/443.html
		String path = getPath("/html/product/"+ id + ".html");
		File f = new File(path);
		File pf = f.getParentFile();
		if(!pf.exists()){
			pf.mkdirs();
		}
		//输出
		Writer out = null;
		try {
			//加载指定的模型
			//读 UTF-8
			Template template = conf.getTemplate("product.html");
			//输出  写 UTF-8
			out = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			//处理
			template.process(root, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//获取全路径
	public String getPath(String path){
		return servletContext.getRealPath(path);
	}
	
	//声明
	private ServletContext servletContext;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
}
