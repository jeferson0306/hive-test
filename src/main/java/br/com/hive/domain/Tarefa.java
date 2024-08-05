package br.com.hive.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "TAREFAS")
public record Tarefa(@Id String id, String nome, String descricao, String status, LocalDateTime dataDeCriacao,
                     LocalDateTime dataDeAtualizacao) {
}
