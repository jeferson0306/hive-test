package br.com.hive.controller;

import br.com.hive.domain.Tarefa;
import br.com.hive.service.TarefaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping("/listar-tarefas")
    public Flux<Tarefa> buscarTarefas() {
        log.info("inicia a listagem de tarefas");
        final var retorno = tarefaService.buscarTarefas();
        log.info("finaliza a listagem de tarefas");
        return retorno;
    }

    @GetMapping("/listar-tarefas/status")
    public Flux<Tarefa> buscarTarefasPorStatus(@RequestParam final String status) {
        log.info("inicia a listagem de tarefas pelo status {}", status);
        final var retorno = tarefaService.buscarTarefasPorStatus(status);
        log.info("finaliza a listagem de tarefas pelo status {}", status);
        return retorno;
    }

    @PostMapping("/criar-tarefa")
    public Mono<ResponseEntity<Tarefa>> criarTarefa(@RequestBody final Tarefa tarefa) {
        log.info("inicia a criacao da tarefa {}", tarefa);
        final var retorno = tarefaService.criarTarefa(tarefa).map(ResponseEntity::ok).defaultIfEmpty(badRequest().build());
        log.info("finaliza a criacao da tarefa {}", tarefa);
        return retorno;
    }

    @PutMapping("/atualizar-tarefa/{id}")
    public Mono<ResponseEntity<Tarefa>> atualizarTarefaPorId(@PathVariable final String id, @RequestBody final Tarefa tarefa) {
        log.info("inicia a atualizacao da tarefa para o id {}", id);
        final var retorno = tarefaService.atualizarTarefaPorId(id, tarefa).map(ResponseEntity::ok).defaultIfEmpty(notFound().build());
        log.info("finaliza a atualizacao da tarefa para o id {}", id);
        return retorno;
    }

    @DeleteMapping("/deletar-tarefa/{id}")
    public Mono<ResponseEntity<String>> deletarTarefasPorId(@PathVariable final String id) {
        log.info("inicia a exclusao da tarefa para o id {}", id);
        final var retorno = tarefaService.deletarTarefasPorId(id).map(ResponseEntity::ok).defaultIfEmpty(notFound().build());
        log.info("finaliza a exclusao da tarefa para o id {}", id);
        return retorno;
    }

}
