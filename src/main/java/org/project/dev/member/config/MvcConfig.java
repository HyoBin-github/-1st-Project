package org.project.dev.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.productImgUploadDir}")
    private String productImgUploadDir;

    @Value("${file.reviewImgUploadDir}")
    private String reviewImgUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profileImages/**")
                .addResourceLocations("file:" + productImgUploadDir);

        registry.addResourceHandler("/reviewImages/**")
                .addResourceLocations("file:" + reviewImgUploadDir);
    }
}
