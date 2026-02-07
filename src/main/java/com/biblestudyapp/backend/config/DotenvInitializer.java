package com.biblestudyapp.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        // Load .env file
        Dotenv dotenv = Dotenv.configure()
                .directory("./")  // Look in project root
                .ignoreIfMissing()  // Don't fail if .env doesn't exist (production)
                .load();

        // Add .env variables to Spring Environment
        Map<String, Object> dotenvProperties = new HashMap<>();
        dotenv.entries().forEach(entry -> {
            dotenvProperties.put(entry.getKey(), entry.getValue());
        });

        environment.getPropertySources().addFirst(
                new MapPropertySource("dotenvProperties", dotenvProperties)
        );
    }
}