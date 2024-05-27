package com.ssafy.enjoytrip.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.ssafy.enjoytrip.auth.interceptor.JWTInterceptor;


//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Configuration
@EnableAspectJAutoProxy
@MapperScan(basePackages = { "com.ssafy.**.dao" })
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final List<String> patterns = Arrays.asList("/admin", "/user/list");
    private final JWTInterceptor jwtInterceptor;
	private final String uploadFilePath;
	
	public WebMvcConfiguration(@Value("${file.path.upload-files}") String uploadFilePath, JWTInterceptor jwtInterceptor) {
	    this.uploadFilePath = uploadFilePath;
	    this.jwtInterceptor = jwtInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/product/*")
				.addPathPatterns("/product")
				.addPathPatterns("/product/book/*")
				.addPathPatterns("/product/book")
				.addPathPatterns("/member")
				.addPathPatterns("/plan")
				.addPathPatterns("/plan/*")
				.excludePathPatterns("/product/search");
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**").allowedOrigins("*")
	//            .allowedOrigins("http://localhost:8080", "http://localhost:8081")
	                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
	                        HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
	                        HttpMethod.PATCH.name())
	                .maxAge(1800); // 1800초 동안 preflight 결과를 캐시에 저장
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("index2");
	}
	
	@Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**") // --1
                .addResourceLocations("file:src/main/data/image/"); //--2
    }
}