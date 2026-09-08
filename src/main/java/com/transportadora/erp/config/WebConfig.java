package com.transportadora.erp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "https://seu-front.vercel.app")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Incluído PATCH aqui!
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}