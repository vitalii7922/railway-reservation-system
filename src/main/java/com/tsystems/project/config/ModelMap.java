package com.tsystems.project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMap {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Bean
    public static ModelMapper modelMapper() {
        return modelMapper;
    }
}
