package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.CityDto;
import com.sharpon.client.lookup.service.CityService;
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
class CityControllerTest {

    @Mock
    CityService cityService;

    @InjectMocks
    CityController cityController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        CityDto dto = CityDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(cityService.save(any())).willReturn(dto);

        CityDto response = cityController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(cityService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(cityService).deleteById(anyLong());
        ResponseEntity<Void> response = cityController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<CityDto> expected = new PageImpl<>(
                Arrays.asList(
                        CityDto.builder().id(1L).code("001").name("Item 1").build(),
                        CityDto.builder().id(2L).code("002").name("Item 2").build(),
                        CityDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(cityService.search(any())).thenReturn(expected);

        ResponseEntity<Page<CityDto>> response = cityController.search(Pageable.unpaged());
        Page<CityDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(cityService, times(1)).search(Pageable.unpaged());
    }

}
