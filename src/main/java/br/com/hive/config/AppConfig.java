package br.com.hive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import software.amazon.awssdk.services.lambda.LambdaClient;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    @Bean
    public ObjectMapper objectMapper() {
        final var objectMapper = new ObjectMapper();
        final var module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(DATETIME_FORMATTER));
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public LambdaClient lambdaClient() {
        return LambdaClient.create();
    }

}

