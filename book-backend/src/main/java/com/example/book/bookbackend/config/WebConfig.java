package com.example.book.bookbackend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * No Used, because we add the cors setting in gateway. and our frontend will call backend through gateway.
 */
//@Configuration
public class WebConfig
{
    // add options in allowed method ,   support preflight request.
/*    static final String ALLOWED_ORIGIN_METHODS[] = new String[] { "GET", "POST", "PUT", "DELETE" ,"OPTIONS", "HEAD"};

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods(ALLOWED_ORIGIN_METHODS);
            }
        };
    }*/
}
