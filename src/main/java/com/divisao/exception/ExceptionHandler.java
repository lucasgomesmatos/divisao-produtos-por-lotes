package com.divisao.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionCamposVazios exceptionCamposVazios = new ExceptionCamposVazios();
        List<ExceptionCamposVazios.Campo> campos = new ArrayList<>();

        for (ObjectError error: ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            campos.add(new ExceptionCamposVazios.Campo(nome, mensagem));
        }

        exceptionCamposVazios.setStatus(status.value());
        exceptionCamposVazios.setDataHora(OffsetDateTime.now());
        exceptionCamposVazios.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente");
        exceptionCamposVazios.setCampos(campos);

        return handleExceptionInternal(ex, exceptionCamposVazios, headers, status, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ExceptionCamposVazios exceptionCamposVazios = new ExceptionCamposVazios();
        exceptionCamposVazios.setStatus(status.value());
        exceptionCamposVazios.setDataHora(OffsetDateTime.now());
        exceptionCamposVazios.setTitulo(ex.getMessage());

        return handleExceptionInternal(ex, exceptionCamposVazios, new HttpHeaders(), status, request);
    }
}
