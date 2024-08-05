package br.com.hive.domain.exception;

public class TarefaNaoEncontradaException extends RuntimeException {

    public TarefaNaoEncontradaException(String id) {
        super("Tarefa não encontrado para o id: " + id);
    }
}
