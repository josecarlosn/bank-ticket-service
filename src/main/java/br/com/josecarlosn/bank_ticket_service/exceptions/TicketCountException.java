package br.com.josecarlosn.bank_ticket_service.exceptions;

public class TicketCountException extends RuntimeException{
    public TicketCountException(){super("Invalid TicketCount.");}
    public TicketCountException(String message){super(message);}
}
