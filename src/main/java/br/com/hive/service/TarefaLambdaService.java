package br.com.hive.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TarefaLambdaService {

    private final LambdaClient lambdaClient;

    public String invokeLambdaFunction(String functionName, Map<String, Object> payload) {
        try {
            final var jsonPayload = new ObjectMapper().writeValueAsString(payload);
            final var payloadBytes = SdkBytes.fromString(jsonPayload, StandardCharsets.UTF_8);
            final var request = InvokeRequest.builder().functionName(functionName).payload(payloadBytes).build();
            final var response = lambdaClient.invoke(request);
            final var responsePayload = response.payload().asUtf8String();
            log.info("Resposta da função Lambda: {}", responsePayload);
            return responsePayload;
        } catch (Exception e) {
            log.error("Erro ao invocar a função Lambda:", e);
            throw new RuntimeException(e);
        }
    }
}
