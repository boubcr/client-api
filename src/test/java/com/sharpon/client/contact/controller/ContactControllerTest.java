package com.sharpon.client.contact.controller;

import com.sharpon.client.contact.controller.ContactController;
import com.sharpon.client.contact.dto.ContactCriteria;
import com.sharpon.client.contact.dto.ContactDto;
import com.sharpon.client.contact.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @Mock
    ContactService contactService;

    @InjectMocks
    ContactController contactController;

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(contactService).deleteById(anyLong());
        ResponseEntity<Void> response = contactController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<ContactDto> expected = new PageImpl<>(
                Arrays.asList(
                        ContactDto.builder().id(1L).value("Item 1").build(),
                        ContactDto.builder().id(2L).value("Item 2").build(),
                        ContactDto.builder().id(3L).value("Item 3").build()
                )
        );

        when(contactService.search(any(ContactCriteria.class), any(Pageable.class))).thenReturn(expected);

        ContactCriteria criteria = ContactCriteria.builder().build();
        ResponseEntity<Page<ContactDto>> response = contactController.search(criteria, Pageable.unpaged());
        Page<ContactDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(contactService, times(1)).search(criteria, Pageable.unpaged());
    }

    @Test
    void update_withValidInput_updatesSuccessfully() {
        ContactDto dto = ContactDto.builder().id(1L).value("Item 1").build();
        when(contactService.update(any(ContactDto.class))).thenReturn(dto);
        ContactDto response = contactController.update(dto);
        assertEquals(dto, response);
    }

    @Test
    void creates_withValidInput_createsSuccessfully() {
        ContactDto dto = ContactDto.builder().id(1L).value("Item 1").build();
        when(contactService.create(any(ContactDto.class), anyLong())).thenReturn(dto);
        ContactDto response = contactController.create(dto, 1L);
        assertEquals(dto, response);
    }

}
