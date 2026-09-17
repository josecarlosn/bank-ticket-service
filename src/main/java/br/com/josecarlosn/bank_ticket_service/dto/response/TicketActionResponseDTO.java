package br.com.josecarlosn.bank_ticket_service.dto.response;

public record TicketActionResponseDTO(
        Long id,
        String code,
        int deskNumber
) {
}
