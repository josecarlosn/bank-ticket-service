package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.request.TicketCountRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.request.TicketRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.TicketCountResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.TicketCount;
import br.com.josecarlosn.bank_ticket_service.exceptions.TicketCountException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.repository.TicketCountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketCountServiceTest {

    @Mock
    private TicketCountRepository ticketCountRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private TicketCountService service;

    @Test
    void list_shouldReturnMappedList() {
        TicketCount ticketCount = new TicketCount(1, false, LocalDate.now());
        when(ticketCountRepository.findAll(any(Sort.class))).thenReturn(List.of(ticketCount));

        List<TicketCountResponseDTO> result = service.list();

        assertEquals(1, result.size());
    }

    @Test
    void create_shouldThrow_whenDateIsInTheFuture() {
        TicketCountRequestDTO dto = new TicketCountRequestDTO(1, false, LocalDate.now().plusDays(1));

        assertThrows(TicketCountException.class, () -> service.create(dto));
        verify(ticketCountRepository, never()).save(any());
    }

    @Test
    void create_shouldThrow_whenDepartmentDoesNotExist() {
        TicketCountRequestDTO dto = new TicketCountRequestDTO(1, false, LocalDate.now());
        when(departmentRepository.existsById(1)).thenReturn(false);

        assertThrows(TicketCountException.class, () -> service.create(dto));
        verify(ticketCountRepository, never()).save(any());
    }

    @Test
    void create_shouldIncrementLastNumber_whenTicketCountExists() {
        LocalDate today = LocalDate.now();
        TicketCountRequestDTO dto = new TicketCountRequestDTO(1, false, today);
        TicketCount existing = mock(TicketCount.class);
        when(existing.getLastNumber()).thenReturn(5);
        when(departmentRepository.existsById(1)).thenReturn(true);
        when(ticketCountRepository.findByDepartmentIdAndHavePriorityAndDate(1, false, today))
                .thenReturn(Optional.of(existing));
        when(ticketCountRepository.save(existing)).thenReturn(existing);

        TicketCount result = service.create(dto);

        verify(existing).setLastNumber(6);
        assertSame(existing, result);
    }

    @Test
    void create_shouldCreateNewTicketCount_whenNoneExists() {
        LocalDate today = LocalDate.now();
        TicketCountRequestDTO dto = new TicketCountRequestDTO(1, true, today);
        when(departmentRepository.existsById(1)).thenReturn(true);
        when(ticketCountRepository.findByDepartmentIdAndHavePriorityAndDate(1, true, today))
                .thenReturn(Optional.empty());
        when(ticketCountRepository.save(any(TicketCount.class))).thenAnswer(inv -> inv.getArgument(0));

        TicketCount result = service.create(dto);

        assertNotNull(result);
        verify(ticketCountRepository).save(any(TicketCount.class));
    }

    @Test
    void nextTicketNumber_shouldReturnLastNumberPlusOne_whenCountExists() {
        TicketCount existing = mock(TicketCount.class);
        when(existing.getLastNumber()).thenReturn(7);
        when(ticketCountRepository.findByDepartmentIdAndHavePriorityAndDate(1, false, LocalDate.now()))
                .thenReturn(Optional.of(existing));

        Integer next = service.nextTicketNumber(new TicketRequestDTO(1, false));

        assertEquals(8, next);
    }

    @Test
    void nextTicketNumber_shouldReturnOne_whenNoCountExists() {
        when(ticketCountRepository.findByDepartmentIdAndHavePriorityAndDate(1, false, LocalDate.now()))
                .thenReturn(Optional.empty());

        Integer next = service.nextTicketNumber(new TicketRequestDTO(1, false));

        assertEquals(1, next);
    }

    @Test
    void delete_shouldThrow_whenTicketCountDoesNotExist() {
        when(ticketCountRepository.existsById(1)).thenReturn(false);

        assertThrows(TicketCountException.class, () -> service.delete(1));
        verify(ticketCountRepository, never()).deleteById(any());
    }

    @Test
    void delete_shouldDelete_whenTicketCountExists() {
        when(ticketCountRepository.existsById(1)).thenReturn(true);

        service.delete(1);

        verify(ticketCountRepository).deleteById(1);
    }
}