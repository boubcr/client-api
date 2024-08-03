package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.ProvinceDto;
import com.sharpon.client.lookup.service.ProvinceService;
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
class ProvinceControllerTest {

    @Mock
    ProvinceService provinceService;

    @InjectMocks
    ProvinceController provinceController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        ProvinceDto dto = ProvinceDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(provinceService.save(any())).willReturn(dto);

        ProvinceDto response = provinceController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(provinceService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(provinceService).deleteById(anyLong());
        ResponseEntity<Void> response = provinceController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<ProvinceDto> expected = new PageImpl<>(
                Arrays.asList(
                        ProvinceDto.builder().id(1L).code("001").name("Item 1").build(),
                        ProvinceDto.builder().id(2L).code("002").name("Item 2").build(),
                        ProvinceDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(provinceService.search(any())).thenReturn(expected);

        ResponseEntity<Page<ProvinceDto>> response = provinceController.search(Pageable.unpaged());
        Page<ProvinceDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(provinceService, times(1)).search(Pageable.unpaged());
    }

}
