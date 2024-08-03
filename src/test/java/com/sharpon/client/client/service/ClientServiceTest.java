package com.sharpon.client.client.service;

import com.sharpon.client.client.dto.ClientCriteria;
import com.sharpon.client.client.dto.ClientDto;
import com.sharpon.client.client.entity.Client;
import com.sharpon.client.client.mapper.ClientMapper;
import com.sharpon.client.client.repository.ClientRepository;
import com.sharpon.client.client.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    ClientRepository repository;

    @Mock
    ClientMapper mapper;

    @InjectMocks
    ClientService service;

    private Client entity;
    private ClientDto dto;

    @BeforeEach
    public void setUp() {
        dto = ClientDto.builder()
                .id(1L)
                .code("100000000001")
                .build();

        entity = new Client();
        entity.setId(1L);
        entity.setCode("100000000001");
    }

    @Test
    void findById_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.findById(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void findById_withNotExistingId_throwsEntityNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Executable executable = () -> service.findById(1L);
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void findById_withValidId_findsSuccessfully() {
        when(mapper.toDto(entity)).thenReturn(dto);
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        ClientDto actual = service.findById(1L);
        assertEquals(dto, actual);
    }

    @Test
    void delete_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.deleteById(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void delete_withNotExistingId_throwsEntityNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Executable executable = () -> service.deleteById(1L);
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void delete_withValidId_deletesSuccessfully() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(any(Client.class));
        service.deleteById(1L);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void create_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.create(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void create_withValidInputs_createsSuccessfully() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Client.class))).thenReturn(entity);
        service.create(dto);
        verify(repository, times(1)).save(any(Client.class));
    }

    @Test
    void update_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.update(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void update_withNotExistingId_throwsEntityNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Executable executable = () -> service.update(dto);
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void update_withValidInput_updatesSuccessfully() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapper.partialUpdate(dto, entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);
        when(repository.save(any())).thenReturn(entity);

        ClientDto actual = service.update(dto);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(entity.getVersion(), actual.getVersion()),
                () -> assertEquals(entity.getCode(), actual.getCode())
        );
    }


    @Test
    void search_withNullInputs_findAllRecords() {
        Page<Client> expected = new PageImpl<>(Collections.singletonList(entity));
        when(mapper.toDto(any(Client.class))).thenReturn(dto);
        when(repository.findAll(any(Pageable.class))).thenReturn(expected);

        Page<ClientDto> actual = service.search(null, null);

        assertEquals(dto.getId(), actual.getContent().get(0).getId());
        assertEquals(dto.getVersion(), actual.getContent().get(0).getVersion());
    }

    @Test
    void search_withValidInputs_shouldReturnList() {
        Page<Client> expected = new PageImpl<>(Collections.singletonList(entity));

        when(mapper.toEntity(any(ClientCriteria.class))).thenReturn(entity);
        when(mapper.toDto(any(Client.class))).thenReturn(dto);
        when(repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(expected);

        ClientCriteria criteria = ClientCriteria.builder().build();
        Page<ClientDto> actual = service.search(criteria, Pageable.unpaged());

        assertEquals(dto.getId(), actual.getContent().get(0).getId());
        assertEquals(dto.getVersion(), actual.getContent().get(0).getVersion());
    }


}
