package br.com.hive.service;

import br.com.hive.domain.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static software.amazon.awssdk.core.sync.RequestBody.fromString;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    public void enviarArquivo(final String nomeBucket, final String tarefaS3BucketJson) throws S3Exception {
        try {
            log.info("iniciando o envio do arquivo para o s3 bucket da aws {}", nomeBucket);
            final var config = PutObjectRequest.builder().bucket(nomeBucket).contentType("application/json").build();
            s3Client.putObject(config, fromString(tarefaS3BucketJson));
            log.info("finalizando o envio do arquivo para o s3 bucket da aws {} com o arquivo {}", nomeBucket, tarefaS3BucketJson);
        } catch (Exception e) {
            log.info("finalizando o envio do arquivo para o s3 bucket da aws {} com erro para o arquivo {} e causa do erro: ", nomeBucket, tarefaS3BucketJson);
            throw new S3Exception(e);
        }
    }

}
