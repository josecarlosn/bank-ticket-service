package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.request.DepartmentRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DepartmentResponseDTO;
import br.com.josecarlosn.bank_ticket_service.dto.update.DepartmentUpdateDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDepartmentException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository repository;
    @Mock
    private DeskRepository deskRepository;

    @InjectMocks
    private DepartmentService service;

    @Test
    void listActiveDepartment_shouldReturnMappedList() {
        Department department = new Department("Caixa", "C", "CP");
        when(repository.findByIsActiveTrue(any(Sort.class))).thenReturn(List.of(department));

        List<DepartmentResponseDTO> result = service.listActiveDepartment();

        assertEquals(1, result.size());
    }

    @Test
    void listAllDepartments_shouldReturnMappedList() {
        Department department = new Department("Caixa", "C", "CP");
        when(repository.findAll(any(Sort.class))).thenReturn(List.of(department));

        List<DepartmentResponseDTO> result = service.listAllDepartments();

        assertEquals(1, result.size());
    }

    @Test
    void create_shouldSaveDepartment_whenDataIsValid() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO("Caixa", "C", "CP");
        when(repository.existsByName("Caixa")).thenReturn(false);
        when(repository.existsByTag("C")).thenReturn(false);
        when(repository.existsByPriorityTag("CP")).thenReturn(false);
        when(repository.findByIsActiveTrue(any(Sort.class))).thenReturn(List.of());

        service.create(dto);

        verify(repository).save(any(Department.class));
    }

    @Test
    void create_shouldThrow_whenNameAlreadyExists() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO("Caixa", "C", "CP");
        when(repository.existsByName("Caixa")).thenReturn(true);

        assertThrows(InvalidDepartmentException.class, () -> service.create(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void create_shouldThrow_whenTagAlreadyExists() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO("Caixa", "C", "CP");
        when(repository.existsByName("Caixa")).thenReturn(false);
        when(repository.existsByTag("C")).thenReturn(true);

        assertThrows(InvalidDepartmentException.class, () -> service.create(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void create_shouldThrow_whenPriorityTagAlreadyExists() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO("Caixa", "C", "CP");
        when(repository.existsByName("Caixa")).thenReturn(false);
        when(repository.existsByTag("C")).thenReturn(false);
        when(repository.existsByPriorityTag("CP")).thenReturn(true);

        assertThrows(InvalidDepartmentException.class, () -> service.create(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void update_shouldChangeOnlyProvidedFields() {
        Department department = new Department("Caixa", "C", "CP");
        when(repository.findById(1)).thenReturn(Optional.of(department));

        service.update(1, new DepartmentUpdateDTO("Gerência", null, null));

        assertEquals("Gerência", department.getName());
        assertEquals("C", department.getTag());
        assertEquals("CP", department.getPriorityTag());
    }

    @Test
    void update_shouldThrow_whenDepartmentNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidDepartmentException.class,
                () -> service.update(1, new DepartmentUpdateDTO("X", null, null)));
    }

    @Test
    void delete_shouldThrow_whenDepartmentDoesNotExist() {
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(InvalidDepartmentException.class, () -> service.delete(1));
        verify(repository, never()).deleteById(any());
    }

    @Test
    void delete_shouldThrow_whenDepartmentHasDesks() {
        when(repository.existsById(1)).thenReturn(true);
        when(deskRepository.existsByDepartmentId(1)).thenReturn(true);

        assertThrows(InvalidDepartmentException.class, () -> service.delete(1));
        verify(repository, never()).deleteById(any());
    }

    @Test
    void delete_shouldDelete_whenNoDesksLinked() {
        when(repository.existsById(1)).thenReturn(true);
        when(deskRepository.existsByDepartmentId(1)).thenReturn(false);

        service.delete(1);

        verify(repository).deleteById(1);
    }

    @Test
    void activate_shouldActivateDepartmentAndItsDesks() {
        Department department = mock(Department.class);
        Desk desk = mock(Desk.class);
        when(repository.findById(1)).thenReturn(Optional.of(department));
        when(deskRepository.findAllByDepartmentId(1)).thenReturn(List.of(desk));

        service.activate(1);

        verify(desk).activate();
        verify(department).activate();
        verify(deskRepository).saveAll(List.of(desk));
        verify(repository).save(department);
    }

    @Test
    void activate_shouldThrow_whenDepartmentNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidDepartmentException.class, () -> service.activate(1));
    }

    @Test
    void deactivate_shouldDeactivateDepartmentAndItsDesks() {
        Department department = mock(Department.class);
        Desk desk = mock(Desk.class);
        when(repository.findById(1)).thenReturn(Optional.of(department));
        when(deskRepository.findAllByDepartmentId(1)).thenReturn(List.of(desk));

        service.deactivate(1);

        verify(desk).deactivate();
        verify(department).deactivate();
        verify(deskRepository).saveAll(List.of(desk));
        verify(repository).save(department);
    }

    @Test
    void deactivate_shouldThrow_whenDepartmentNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidDepartmentException.class, () -> service.deactivate(1));
    }
}