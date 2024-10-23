package edu.csumb.project_2;  // Make sure to use the correct package

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "https://wishlistapi-b5777d959cf8.herokuapp.com")  // Allow local dev and production
            .allowedMethods("GET", "POST", "PATCH", "DELETE", "PUT")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}