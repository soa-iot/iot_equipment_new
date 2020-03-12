
/**
 * <一句话功能描述>
 * <p>备份文件映射路径配置
 * @author 陈宇林
 * @version [版本号, 2020年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class BackFileConfig implements WebMvcConfigurer {

	@Value("${filePath.backup.real}")
	private String realPath;

	@Value("${filePath.backup.virtual}")
	private String virtualPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler(virtualPath + "/**").addResourceLocations("file:" + realPath);

		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

}
