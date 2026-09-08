package br.com.josecarlosn.bank_ticket_service.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestExceptionMessage {
    private HttpStatus httpStatus;
    private String message;
}
