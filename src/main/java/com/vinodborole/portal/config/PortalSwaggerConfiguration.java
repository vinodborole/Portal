/**
 * 
 */
package com.vinodborole.portal.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author vinodborole
 *
 */
@Configuration
@EnableSwagger2
public class PortalSwaggerConfiguration {
	@Bean
	public Docket api() {
		List<Parameter> aParameters = new ArrayList<Parameter>();
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").build();
		Parameter authorizationHeader = aParameterBuilder.build();
		aParameters.add(authorizationHeader);

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().globalOperationParameters(aParameters);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Portal Platform API")
				.description("Portal Platform API reference for developers")
				.termsOfServiceUrl("http://www.vinodborole.com").contact("vborole@gmail.com").license("Apache License")
				.licenseUrl("vborole@gmail.com").version("1.0").build();
	}
}
