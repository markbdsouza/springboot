package com.appdeveloperblog.app.ws;

import com.appdeveloperblog.app.ws.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class MobileAppWsApplication extends SpringBootServletInitializer
{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MobileAppWsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}

	@Bean(name="AppProperties")
	public AppProperties appProperties(){
		return new AppProperties();
	}

//	@Bean
//	public Docket apiDocket() {
//		Docket docket = new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.appdeveloperblog.app.ws"))
//				.paths(PathSelectors.any())
//				.build();
//		return docket;
//	}
//
//	@Bean
//	public LinkDiscoverers discoverers() {
//		List<LinkDiscoverer> plugins = new ArrayList<>();
//		plugins.add(new CollectionJsonLinkDiscoverer());
//		return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
//	}

}
