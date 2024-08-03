package com.sharpon.client.address.service;

import com.sharpon.client.address.dto.AddressCriteria;
import com.sharpon.client.address.dto.AddressDto;
import com.sharpon.client.address.entity.Address;
import com.sharpon.client.address.mapper.AddressMapper;
import com.sharpon.client.address.repository.AddressRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    AddressRepository repository;

    @Mock
    AddressMapper mapper;

    @InjectMocks
    AddressService service;

    private Address entity;
    private AddressDto dto;

    @BeforeEach
    public void setUp() {
        dto = AddressDto.builder()
                .id(1L)
                .street("45 Streat A")
                .postalCode("N2N N2N")
                .enabled(true)
                .build();

        entity = new Address();
        entity.setId(1L);
        entity.setStreet("45 Streat A");
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
    void findById_withValidUserId_findsSuccessfully() {
        when(mapper.toDto(entity)).thenReturn(dto);
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        AddressDto actual = service.findById(1L);
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
    void delete_withValidUserId_deletesSuccessfully() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(any(Address.class));
        service.deleteById(1L);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void create_withNullUser_throwsIllegalArgumentException() {
        Executable executable = () -> service.create(null, 1L);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void create_withNullClientId_throwsIllegalArgumentException() {
        Executable executable = () -> service.create(dto, null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void create_withValidInputs_createsSuccessfully() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Address.class))).thenReturn(entity);
        service.create(dto, 1L);
        verify(repository, times(1)).save(any(Address.class));
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

        AddressDto actual = service.update(dto);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(entity.getVersion(), actual.getVersion()),
                () -> assertEquals(entity.getStreet(), actual.getStreet())
        );
    }

    @Test
    void search_withNullInputs_findAllRecords() {
        Page<Address> expected = new PageImpl<>(Collections.singletonList(entity));
        when(mapper.toDto(any(Address.class))).thenReturn(dto);
        when(repository.findAll(any(Pageable.class))).thenReturn(expected);

        Page<AddressDto> actual = service.search(null, null);

        assertEquals(dto.getId(), actual.getContent().get(0).getId());
        assertEquals(dto.getVersion(), actual.getContent().get(0).getVersion());
    }

    @Test
    void search_withValidInputs_shouldReturnList() {
        Page<Address> expected = new PageImpl<>(Collections.singletonList(entity));

        when(mapper.toEntity(any(AddressCriteria.class))).thenReturn(entity);
        when(mapper.toDto(any(Address.class))).thenReturn(dto);
        when(repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(expected);

        AddressCriteria criteria = AddressCriteria.builder().build();
        Page<AddressDto> actual = service.search(criteria, Pageable.unpaged());

        assertEquals(dto.getId(), actual.getContent().get(0).getId());
        assertEquals(dto.getVersion(), actual.getContent().get(0).getVersion());
    }


}
