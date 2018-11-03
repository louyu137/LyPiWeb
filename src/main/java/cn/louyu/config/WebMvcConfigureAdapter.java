package cn.louyu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigureAdapter implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //把 classpath:/templates/layuiadmin/ 路径文件映射到localtion:8080/layuiadmin/**
        registry.addResourceHandler("/layuiadmin/**").addResourceLocations("classpath:/templates/layuiadmin/");
    }
}
