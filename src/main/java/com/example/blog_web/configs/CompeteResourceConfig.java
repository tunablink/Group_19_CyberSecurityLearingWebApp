package com.example.blog_web.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CompeteResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/compete-static/**")
                .addResourceLocations("classpath:/templates/compete/styles/");

        registry.addResourceHandler("/compete-imports/**")
                .addResourceLocations("classpath:/templates/compete/imports/");
    }
}
