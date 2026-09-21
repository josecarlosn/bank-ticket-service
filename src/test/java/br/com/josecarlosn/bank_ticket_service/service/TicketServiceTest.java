package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.request.TicketActionRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.request.TicketRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.CustomerTicketPanelResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.entity.Ticket;
import br.com.josecarlosn.bank_ticket_service.exceptions.TicketException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import br.com.josecarlosn.bank_ticket_service.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private TicketCountService ticketCountService;
    @Mock
    private DeskRepository deskRepository;
    @Mock
    private SimpMessagingTemplate messageTemplate;

    @InjectMocks
    private TicketService service;

    @Test
    void list_shouldReturnMappedList() {
        Ticket ticket = new Ticket(new Department("Caixa", "C", "CP"), false, 1, LocalDateTime.now());
        when(ticketRepository.findAll(any(Sort.class))).thenReturn(List.of(ticket));

        assertEquals(1, service.list().size());
    }

    @Test
    void generateTicket_shouldSaveTicketWithCodeAndNotifyPanel() {
        Department department = new Department("Caixa", "C", "CP");
        TicketRequestDTO dto = new TicketRequestDTO(1, false);
        when(ticketCountService.nextTicketNumber(dto)).thenReturn(1);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        service.generateTicket(dto);

        ArgumentCaptor<Ticket> captor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketCountService).create(any());
        verify(ticketRepository).save(captor.capture());
        assertEquals("C001", captor.getValue().getCode());
        verify(messageTemplate).convertAndSend(eq("/topic/ticket/internal/panel"), any(Object.class));
    }

    @Test
    void generateTicket_shouldUsePriorityTag_whenTicketHasPriority() {
        Department department = new Department("Caixa", "C", "CP");
        TicketRequestDTO dto = new TicketRequestDTO(1, true);
        when(ticketCountService.nextTicketNumber(dto)).thenReturn(12);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        service.generateTicket(dto);

        ArgumentCaptor<Ticket> captor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository).save(captor.capture());
        assertEquals("CP012", captor.getValue().getCode());
    }

    @Test
    void generateTicket_shouldThrow_whenDepartmentNotFound() {
        TicketRequestDTO dto = new TicketRequestDTO(1, false);
        when(ticketCountService.nextTicketNumber(dto)).thenReturn(1);
        when(departmentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TicketException.class, () -> service.generateTicket(dto));
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void call_shouldCallTicketAndNotifyCustomerPanel() {
        Ticket ticket = mock(Ticket.class, RETURNS_DEEP_STUBS);
        Desk desk = mock(Desk.class, RETURNS_DEEP_STUBS);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(deskRepository.findById(2)).thenReturn(Optional.of(desk));

        CustomerTicketPanelResponseDTO result = service.call(1L, new TicketActionRequestDTO(2));

        assertNotNull(result);
        verify(ticket).call(any(), eq(desk));
        verify(ticketRepository).save(ticket);
        verify(messageTemplate).convertAndSend(eq("/topic/ticket/customer/panel"), any(Object.class));
    }

    @Test
    void call_shouldThrow_whenTicketNotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TicketException.class, () -> service.call(1L, new TicketActionRequestDTO(2)));
    }

    @Test
    void call_shouldThrow_whenDeskNotFound() {
        Ticket ticket = mock(Ticket.class);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(deskRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(TicketException.class, () -> service.call(1L, new TicketActionRequestDTO(2)));
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void finish_shouldFinishTicketAndNotifyInternalPanel() {
        Ticket ticket = mock(Ticket.class, RETURNS_DEEP_STUBS);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        service.finish(1L);

        verify(ticket).finish();
        verify(ticketRepository).save(ticket);
        verify(messageTemplate).convertAndSend(eq("/topic/ticket/internal/panel"), any(Object.class));
    }

    @Test
    void finish_shouldThrow_whenTicketNotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TicketException.class, () -> service.finish(1L));
    }

    @Test
    void cancel_shouldCancelTicketAndNotifyInternalPanel() {
        Ticket ticket = mock(Ticket.class, RETURNS_DEEP_STUBS);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        service.cancel(1L);

        verify(ticket).cancel();
        verify(ticketRepository).save(ticket);
        verify(messageTemplate).convertAndSend(eq("/topic/ticket/internal/panel"), any(Object.class));
    }

    @Test
    void cancel_shouldThrow_whenTicketNotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TicketException.class, () -> service.cancel(1L));
    }
}