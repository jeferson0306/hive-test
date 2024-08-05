package br.com.hive.repository;

import br.com.hive.domain.Tarefa;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TarefaRepository extends ReactiveMongoRepository<Tarefa, String> {

    Flux<Tarefa> findByStatus(String status);
}
