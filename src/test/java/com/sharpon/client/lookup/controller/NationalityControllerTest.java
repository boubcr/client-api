package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.NationalityDto;
import com.sharpon.client.lookup.service.NationalityService;
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
class NationalityControllerTest {

    @Mock
    NationalityService nationalityService;

    @InjectMocks
    NationalityController nationalityController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        NationalityDto dto = NationalityDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(nationalityService.save(any())).willReturn(dto);

        NationalityDto response = nationalityController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(nationalityService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(nationalityService).deleteById(anyLong());
        ResponseEntity<Void> response = nationalityController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<NationalityDto> expected = new PageImpl<>(
                Arrays.asList(
                        NationalityDto.builder().id(1L).code("001").name("Item 1").build(),
                        NationalityDto.builder().id(2L).code("002").name("Item 2").build(),
                        NationalityDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(nationalityService.search(any())).thenReturn(expected);

        ResponseEntity<Page<NationalityDto>> response = nationalityController.search(Pageable.unpaged());
        Page<NationalityDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(nationalityService, times(1)).search(Pageable.unpaged());
    }

}
