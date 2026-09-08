package br.com.josecarlosn.bank_ticket_service.infra;

import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDepartmentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidDepartmentException.class)
    public ResponseEntity<RestExceptionMessage> departmentHandle(InvalidDepartmentException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestExceptionMessage(HttpStatus.CONFLICT, exception.getMessage()));
    }
}
