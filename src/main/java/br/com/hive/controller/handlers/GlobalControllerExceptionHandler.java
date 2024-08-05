package br.com.hive.controller.handlers;

import br.com.hive.domain.exception.TarefaNaoEncontradaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(NO_CONTENT)
    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<String> handleTarefaNaoEncontradaException(final TarefaNaoEncontradaException tarefaNaoEncontradaException) {
        log.error("Tarefa não encontrada", tarefaNaoEncontradaException);
        return new ResponseEntity<>(tarefaNaoEncontradaException.getMessage(), NO_CONTENT);
    }
}
