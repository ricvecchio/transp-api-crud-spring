package com.transportadora.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Entrou aqui - CorsConfig - addCorsMappings"); // EXCLUIR
        registry.addMapping("/**")
                .allowedOrigins("https://saotomecatimesaotomecatime.com")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*");
    }
}
