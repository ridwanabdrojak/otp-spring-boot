package bni.otp.config;

import bni.otp.repository.ErrorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorConfig {

    @Bean
    public ErrorRepository errorRepository() {
        return new ErrorRepository();
    }
}