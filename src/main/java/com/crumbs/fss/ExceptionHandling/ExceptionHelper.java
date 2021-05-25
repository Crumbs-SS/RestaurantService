package com.crumbs.fss.ExceptionHandling;

import javassist.bytecode.DuplicateMemberException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        body.put("message", ex.getCause());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmail(){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", " Restaurant owner with this email was already found in database. Please enter new e-mail address.");
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DuplicateLocationException.class)
    public ResponseEntity<Object> handleDuplicateLocation(){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", " This location already exists in database. Please enter new location.");
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


}
