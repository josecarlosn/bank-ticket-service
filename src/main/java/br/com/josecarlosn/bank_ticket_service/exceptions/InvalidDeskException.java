package br.com.josecarlosn.bank_ticket_service.exceptions;

public class InvalidDeskException extends RuntimeException{
    public InvalidDeskException(){super("Some desk data already exists.");}
    public InvalidDeskException(String message){super(message);}
}
