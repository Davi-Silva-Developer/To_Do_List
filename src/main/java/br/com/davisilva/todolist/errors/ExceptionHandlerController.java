package br.com.davisilva.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;




//  indica que esta classe
// trata exceções de forma GLOBAL na aplicação
@ControllerAdvice

public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<Map<String, String>> handleValidationExceptions(    // Este método será executado sempre que uma validação falhar
            MethodArgumentNotValidException ex) {

        // Map para guardar os erros no formato:
        // campo -> mensagem
        Map<String, String> errors = new HashMap<>();

        // Percorre todos os erros de validação encontrados
        ex.getBindingResult().getFieldErrors().forEach(error ->  errors.put( // Pega o nome do campo (ex: "title")
                        error.getField(),        // e a mensagem definida na anotação (@Size, @NotBlank)
                        error.getDefaultMessage()
                )

        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)  // com as mensagens de erro no corpo da resposta
                .body(errors);                   // Retorna HTTP 400 (Bad Request)

    }
}