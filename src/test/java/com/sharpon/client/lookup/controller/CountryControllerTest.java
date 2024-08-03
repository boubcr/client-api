package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.CountryDto;
import com.sharpon.client.lookup.service.CountryService;
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
class CountryControllerTest {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        CountryDto dto = CountryDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(countryService.save(any())).willReturn(dto);

        CountryDto response = countryController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(countryService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(countryService).deleteById(anyLong());
        ResponseEntity<Void> response = countryController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<CountryDto> expected = new PageImpl<>(
                Arrays.asList(
                        CountryDto.builder().id(1L).code("001").name("Item 1").build(),
                        CountryDto.builder().id(2L).code("002").name("Item 2").build(),
                        CountryDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(countryService.search(any())).thenReturn(expected);

        ResponseEntity<Page<CountryDto>> response = countryController.search(Pageable.unpaged());
        Page<CountryDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(countryService, times(1)).search(Pageable.unpaged());
    }

}
