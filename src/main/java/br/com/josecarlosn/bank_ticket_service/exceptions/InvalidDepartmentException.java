package br.com.josecarlosn.bank_ticket_service.exceptions;

public class InvalidDepartmentException extends RuntimeException{
    public InvalidDepartmentException(){super("Some department data already exists.");}
    public InvalidDepartmentException(String message){super(message);}
}
