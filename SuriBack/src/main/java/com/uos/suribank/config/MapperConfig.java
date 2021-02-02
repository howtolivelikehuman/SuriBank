package com.uos.suribank.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    private final ModelMapper modelMapper = new ModelMapper();
    
    @Bean
    public ModelMapper modelMapper(){
        modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setSkipNullEnabled(true);
        return modelMapper;
    }
}
