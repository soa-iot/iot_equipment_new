
/**
 * <一句话功能描述>
 * <p> swagger配置类
 * @author 陈宇林
 * @version [版本号, 2020年3月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// Controller所在包(必须新建包)
				.apis(RequestHandlerSelectors.basePackage("cn.soa.controller.spareparts"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 接口文档的名字
				.title("设备相关接口文档")
				// 接口文档的描述
				.description("设备相关接口文档")
				// 服务条款网址
				.termsOfServiceUrl("http://localhost/")
				// 接口文档的版本
				.version("1.0.0")
				// 接口文档维护联系信息
				.contact(new Contact("chenyulin", "", "707698654@qq.com")).build();
	}
}
