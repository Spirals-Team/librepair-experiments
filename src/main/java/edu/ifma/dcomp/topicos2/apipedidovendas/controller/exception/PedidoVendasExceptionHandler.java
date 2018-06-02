package edu.ifma.dcomp.topicos2.apipedidovendas.controller.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class PedidoVendasExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public PedidoVendasExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }



    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        String mensagem = this.messageSource.getMessage("parametro.invalido", null, LocaleContextHolder.getLocale());


        String causa = ex.getCause().toString();

        return super.handleExceptionInternal (ex, new Erro(mensagem, causa), headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<Erro> erros = carregaListaDeErros(ex.getBindingResult() );

        return super.handleExceptionInternal(ex, erros, headers, status, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

        String mensagem = messageSource.getMessage("recurso.naoencontrado", null, LocaleContextHolder.getLocale() );
        String  causa = ex.toString();

        return super.handleExceptionInternal(ex, new Erro(mensagem, causa), new HttpHeaders(),
                                             HttpStatus.NOT_FOUND, request );

    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        String mensagem = messageSource.getMessage("recurso.operacao-nao-permitida", null,
                                                    LocaleContextHolder.getLocale() );

        String  causa = ex.toString();


        return super.handleExceptionInternal(ex, new Erro(mensagem, causa), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request );
    }



    private List<Erro> carregaListaDeErros(BindingResult bindingResult ) {
        List<Erro> erros = new ArrayList<>();

        bindingResult.getFieldErrors().forEach( fieldError -> {

                    String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale() );
                    erros.add( new Erro(mensagem ,fieldError.toString() ) );
                }
        );
        return erros;

    }

    private final class Erro {

        private final String mensagem;
        private final String causa;

        public Erro(String mensagem, String causa) {
            this.mensagem = mensagem;
            this.causa = causa;
        }

        public String getMensagem() {
            return mensagem;
        }

        public String getCausa() {
            return causa;
        }
    }



}
