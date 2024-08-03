package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.CompanyDto;
import com.sharpon.client.lookup.service.CompanyService;
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
class CompanyControllerTest {

    @Mock
    CompanyService companyService;

    @InjectMocks
    CompanyController companyController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        CompanyDto dto = CompanyDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(companyService.save(any())).willReturn(dto);

        CompanyDto response = companyController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(companyService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(companyService).deleteById(anyLong());
        ResponseEntity<Void> response = companyController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<CompanyDto> expected = new PageImpl<>(
                Arrays.asList(
                        CompanyDto.builder().id(1L).code("001").name("Item 1").build(),
                        CompanyDto.builder().id(2L).code("002").name("Item 2").build(),
                        CompanyDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(companyService.search(any())).thenReturn(expected);

        ResponseEntity<Page<CompanyDto>> response = companyController.search(Pageable.unpaged());
        Page<CompanyDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(companyService, times(1)).search(Pageable.unpaged());
    }

}
