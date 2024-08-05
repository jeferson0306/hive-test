package br.com.hive.service;

import br.com.hive.domain.Tarefa;
import br.com.hive.domain.exception.TarefaNaoEncontradaException;
import br.com.hive.repository.TarefaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

import static java.nio.file.Files.writeString;

@Slf4j
@Service
@RequiredArgsConstructor
public class TarefaService {

    private final S3Client s3Client;
    private final ObjectMapper objectMapper;
    private final TarefaRepository taskRepository;
    private final TarefaLambdaService tarefaLambdaService;

    public Flux<Tarefa> buscarTarefas() {
        log.info("iniciando a listagem de tarefas");
        return taskRepository.findAll();
    }

    public Flux<Tarefa> buscarTarefasPorStatus(final String status) {
        log.info("iniciando a listagem de tarefas pelo status {}", status);
        return taskRepository.findByStatus(status);
    }

    public Mono<Tarefa> criarTarefa(final Tarefa tarefa) {
        log.info("iniciando a criacao da tarefa {}", tarefa);
        return taskRepository.save(tarefa).publishOn(Schedulers.boundedElastic()).flatMap(tarefaSalvaNoBucket -> Mono.fromCallable(() -> {
            try {
                getObjectMapper(tarefaSalvaNoBucket);
                log.info("arquivo enviado para o bucket s3 da aws");
                final var payload = new HashMap<String, Object>();
                payload.put("tarefaId", tarefaSalvaNoBucket.id());
                payload.put("tarefaNome", tarefaSalvaNoBucket.nome());
                tarefaLambdaService.invokeLambdaFunction("hive-test-lambda", payload);
                return tarefaSalvaNoBucket;
            } catch (Exception e) {
                log.error("erro ao enviar para o bucket s3 da aws:", e);
                throw e;
            }
        }).subscribeOn(Schedulers.boundedElastic()));
    }

    public Mono<Tarefa> atualizarTarefaPorId(final String id, final Tarefa tarefa) {
        log.info("iniciando a atualizacao da tarefa para o id {}", id);
        return taskRepository.findById(id).flatMap(existingTask -> {
            final var updatedTask = new Tarefa(id, tarefa.nome(), tarefa.descricao(), tarefa.status(), tarefa.dataDeCriacao(), tarefa.dataDeAtualizacao());
            return taskRepository.save(updatedTask).publishOn(Schedulers.boundedElastic()).flatMap(savedTask -> Mono.fromCallable(() -> {
                try {
                    getObjectMapper(savedTask);
                    log.info("arquivo atualizado enviado para o bucket s3 da aws");
                    return savedTask;
                } catch (Exception e) {
                    log.error("erro ao enviar para o bucket s3 da aws:", e);
                    throw e;
                }
            }).subscribeOn(Schedulers.boundedElastic()));
        }).switchIfEmpty(Mono.empty()).doOnTerminate(() -> log.info("finalizando a atualizacao da tarefa para o id {}", id));
    }

    public Mono<String> deletarTarefasPorId(final String id) {
        log.info("iniciando a exclusao da tarefa para o id {}", id);
        return taskRepository.findById(id).flatMap(existingTask -> taskRepository.deleteById(id).then(Mono.just("Tarefa deletada com sucesso"))).switchIfEmpty(Mono.error(new TarefaNaoEncontradaException("Tarefa não encontrada para o id: " + id))).doOnTerminate(() -> log.info("finalizando a exclusao da tarefa para o id {}", id));
    }

    private void getObjectMapper(final Tarefa tarefaSalva) throws IOException {
        final var json = objectMapper.writeValueAsString(tarefaSalva);
        final var key = UUID.randomUUID() + ".json";
        final var tempFile = Paths.get(System.getProperty("java.io.tmpdir"), key);
        writeString(tempFile, json);
        final var putObjectRequest = PutObjectRequest.builder().bucket("s3-hive-test").key(key).contentType("application/json").build();
        s3Client.putObject(putObjectRequest, tempFile);
    }
}
