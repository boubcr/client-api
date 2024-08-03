package com.sharpon.client.contact.service;

import com.sharpon.client.contact.dto.ContactCriteria;
import com.sharpon.client.contact.dto.ContactDto;
import com.sharpon.client.contact.entity.Contact;
import com.sharpon.client.contact.mapper.ContactMapper;
import com.sharpon.client.contact.repository.ContactRepository;
import com.sharpon.client.contact.service.ContactService;
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
class ContactServiceTest {
    @Mock
    ContactRepository repository;

    @Mock
    ContactMapper mapper;

    @InjectMocks
    ContactService service;

    private Contact entity;
    private ContactDto dto;

    @BeforeEach
    public void setUp() {
        dto = ContactDto.builder()
                .id(1L)
                .value("212635218167")
                .enabled(true)
                .build();

        entity = new Contact();
        entity.setId(1L);
        entity.setValue("212635218167");
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
        ContactDto actual = service.findById(1L);
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
        doNothing().when(repository).delete(any(Contact.class));
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
        when(repository.save(any(Contact.class))).thenReturn(entity);
        service.create(dto, 1L);
        verify(repository, times(1)).save(any(Contact.class));
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

        ContactDto actual = service.update(dto);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(entity.getVersion(), actual.getVersion()),
                () -> assertEquals(entity.getValue(), actual.getValue())
        );
    }

    @Test
    void search_withNullInputs_findAllRecords() {
        Page<Contact> expected = new PageImpl<>(Collections.singletonList(entity));
        when(mapper.toDto(any(Contact.class))).thenReturn(dto);
        when(repository.findAll(any(Pageable.class))).thenReturn(expected);

        Page<ContactDto> actual = service.search(null, null);

        assertEquals(dto.getId(), actual.getContent().get(0).getId());
        assertEquals(dto.getVersion(), actual.getContent().get(0).getVersion());
    }

    @Test
    void search_withValidInputs_shouldReturnList() {
        Page<Contact> expected = new PageImpl<>(Collections.singletonList(entity));

        when(mapper.toEntity(any(ContactCriteria.class))).thenReturn(entity);
        when(mapper.toDto(any(Contact.class))).thenReturn(dto);
        when(repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(expected);

        ContactCriteria criteria = ContactCriteria.builder().build();
        Page<ContactDto> actual = service.search(criteria, Pageable.unpaged());

        assertEquals(dto.getId(), actual.getContent().get(0).getId());
        assertEquals(dto.getVersion(), actual.getContent().get(0).getVersion());
    }


}
