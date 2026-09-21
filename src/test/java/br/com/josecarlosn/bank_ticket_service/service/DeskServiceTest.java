package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.request.DeskRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DeskResponseDTO;
import br.com.josecarlosn.bank_ticket_service.dto.update.DeskUpdateDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDepartmentException;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDeskException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeskServiceTest {

    @Mock
    private DeskRepository deskRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DeskService service;

    @Test
    void list_shouldReturnMappedList() {
        Department department = new Department("Caixa", "C", "CP");
        Desk desk = new Desk(department, 1);
        ReflectionTestUtils.setField(desk, "id", 1);
        when(deskRepository.findAll(any(Sort.class))).thenReturn(List.of(desk));

        List<DeskResponseDTO> result = service.list();

        assertEquals(1, result.size());
    }

    @Test
    void create_shouldSaveDesk_whenDataIsValid() {
        DeskRequestDTO dto = new DeskRequestDTO(1, 1, 1);
        Department department = new Department("Caixa", "C", "CP");
        when(deskRepository.existsByDepartmentIdAndNumber(1, 1)).thenReturn(false);
        when(departmentRepository.existsById(1)).thenReturn(true);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        service.create(dto);

        verify(deskRepository).save(any(Desk.class));
    }

    @Test
    void create_shouldThrow_whenDeskAlreadyExists() {
        DeskRequestDTO dto = new DeskRequestDTO(1, 1, 1);
        when(deskRepository.existsByDepartmentIdAndNumber(1, 1)).thenReturn(true);

        assertThrows(InvalidDeskException.class, () -> service.create(dto));
        verify(deskRepository, never()).save(any());
    }

    @Test
    void create_shouldThrow_whenDepartmentNotFound() {
        DeskRequestDTO dto = new DeskRequestDTO(1, 1, 1);
        when(deskRepository.existsByDepartmentIdAndNumber(1, 1)).thenReturn(false);
        when(departmentRepository.existsById(1)).thenReturn(false);

        assertThrows(InvalidDeskException.class, () -> service.create(dto));
        verify(deskRepository, never()).save(any());
    }

    @Test
    void deactivate_shouldDeactivateAndSave() {
        Desk desk = mock(Desk.class);
        when(deskRepository.findById(1)).thenReturn(Optional.of(desk));

        service.deactivate(1);

        verify(desk).deactivate();
        verify(deskRepository).save(desk);
    }

    @Test
    void deactivate_shouldThrow_whenDeskNotFound() {
        when(deskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidDeskException.class, () -> service.deactivate(1));
    }

    @Test
    void activate_shouldActivateAndSave() {
        Desk desk = mock(Desk.class);
        when(deskRepository.findById(1)).thenReturn(Optional.of(desk));

        service.activate(1);

        verify(desk).activate();
        verify(deskRepository).save(desk);
    }

    @Test
    void activate_shouldThrow_whenDeskNotFound() {
        when(deskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidDeskException.class, () -> service.activate(1));
    }

    @Test
    void update_shouldChangeNumberAndDepartment() {
        Department oldDepartment = new Department("Caixa", "C", "CP");
        Department newDepartment = new Department("Gerência", "G", "GP");
        Desk desk = new Desk(oldDepartment, 1);
        ReflectionTestUtils.setField(desk, "id", 1);
        when(deskRepository.findById(1)).thenReturn(Optional.of(desk));
        when(departmentRepository.findById(2)).thenReturn(Optional.of(newDepartment));

        DeskResponseDTO result = service.update(1, new DeskUpdateDTO(9, 2));

        assertEquals(9, desk.getNumber());
        assertEquals(newDepartment, desk.getDepartment());
        assertNotNull(result);
    }

    @Test
    void update_shouldThrow_whenDeskNotFound() {
        when(deskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidDeskException.class,
                () -> service.update(1, new DeskUpdateDTO(9, null)));
    }

    @Test
    void update_shouldThrow_whenDepartmentNotFound() {
        Desk desk = new Desk(new Department("Caixa", "C", "CP"), 1);
        when(deskRepository.findById(1)).thenReturn(Optional.of(desk));
        when(departmentRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(InvalidDepartmentException.class,
                () -> service.update(1, new DeskUpdateDTO(null, 2)));
    }
}