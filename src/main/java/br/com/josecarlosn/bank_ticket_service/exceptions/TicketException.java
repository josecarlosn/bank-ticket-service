package br.com.josecarlosn.bank_ticket_service.exceptions;

public class TicketException extends RuntimeException {
    public TicketException(){super("Invalid ticket!");}
    public TicketException(String message) {
        super(message);
    }
}
