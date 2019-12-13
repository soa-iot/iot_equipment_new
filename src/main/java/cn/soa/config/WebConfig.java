package cn.soa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置图片映射路径
 * @author Jiang, Hang
 *
 */
@Configuration
@PropertySource(value= {"classpath:config/fileUpload.properties"}, encoding="UTF-8")
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${thick.image.upload.path}")
	private String rootDir;   //保存图片的根路径
	
	@Value("${maintenance.file.upload.path}")
	private String rootDir1;   //文件图片的根路径
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/picture/**")
				.addResourceLocations(rootDir);
		
		registry.addResourceHandler("/picture1/**")
		.addResourceLocations(rootDir1);
	}
}
