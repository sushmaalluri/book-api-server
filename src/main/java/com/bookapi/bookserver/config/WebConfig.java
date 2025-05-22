package com.bookapi.bookserver.config; // Make sure this package matches where you created the file

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all paths in your application (e.g., /books and any other future ones)
                .allowedOrigins(
                    "http://localhost:5173",                     // Your local React dev server
                    "https://cozy-pothos-54424c.netlify.app"     // Your deployed Netlify frontend (good to keep for when you deploy)
                ) 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Explicitly allow these methods
                .allowedHeaders("*") // Allow all headers requested by the client (like Content-Type, Accept, etc.)
                .allowCredentials(false); // Set to true if you were using cookies or HTTP authentication with credentials
                // .maxAge(3600); // Optional: How long the results of a preflight request can be cached by the browser (in seconds)
    }
}