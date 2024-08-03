package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.ContactTypeDto;
import com.sharpon.client.lookup.service.ContactTypeService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactTypeControllerTest {

    @Mock
    ContactTypeService contactTypeService;

    @InjectMocks
    ContactTypeController contactTypeController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        ContactTypeDto dto = ContactTypeDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(contactTypeService.save(any())).willReturn(dto);

        ContactTypeDto response = contactTypeController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(contactTypeService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(contactTypeService).deleteById(anyLong());
        ResponseEntity<Void> response = contactTypeController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<ContactTypeDto> expected = new PageImpl<>(
                Arrays.asList(
                        ContactTypeDto.builder().id(1L).code("001").name("Item 1").build(),
                        ContactTypeDto.builder().id(2L).code("002").name("Item 2").build(),
                        ContactTypeDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(contactTypeService.search(any())).thenReturn(expected);

        ResponseEntity<Page<ContactTypeDto>> response = contactTypeController.search(Pageable.unpaged());
        Page<ContactTypeDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(contactTypeService, times(1)).search(Pageable.unpaged());
    }

}
