package com.adnan.configuration;

import com.adnan.repository.MatchResultRepository;
import com.adnan.service.MatchResultService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public MatchResultService matchResultService(MatchResultRepository matchResultRepository) {
        MatchResultService matchResultService = new MatchResultService(matchResultRepository);
        matchResultService.startEventInsertionThread();
        return matchResultService;
    }
}
