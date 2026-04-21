package com.shivam.catalogservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
// browser requests -> spring responses with these headers -> browser checks Origin Allowed , Method Allowed , Headers Allowed etc .
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/**") //allow request starting with /api/
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOriginPatterns("*") //Allow requests from ANY domain
                .allowCredentials(false) ; //Cookies not allowed
    }
}
