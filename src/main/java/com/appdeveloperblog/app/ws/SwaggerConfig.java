package com.appdeveloperblog.app.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Contact contact = new Contact("Mark Dsouza" , "www.markbdsouza.com", "mark.benjamin.dsouza@gmail.com");
    private Collection<VendorExtension> vendorExtentions = new ArrayList<>();
    ApiInfo apiInfo = new ApiInfo("Photo App restful webservice" ,
            "This point contains endpoints for the photo app web service",
            "1.0",
            "http://www.appsdeveloperblog.com/service.html",
            contact, "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtentions
    );

    @Bean
    public Docket apiDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPs")))
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.appdeveloperblog.app.ws"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

}
