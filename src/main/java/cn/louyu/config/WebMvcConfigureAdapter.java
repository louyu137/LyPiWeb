package cn.louyu.config;

import cn.louyu.service.security.ApiVerifyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigureAdapter implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //把 classpath:/templates/layuiadmin/ 路径文件映射到localtion:8080/layuiadmin/**
        registry.addResourceHandler("/layuiadmin/**").addResourceLocations("classpath:/templates/layuiadmin/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/templates/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/templates/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/templates/img/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiVerifyInterceptor())
                .addPathPatterns("/api/**");
    }
}
