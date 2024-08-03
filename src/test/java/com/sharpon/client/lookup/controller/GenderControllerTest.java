package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.GenderDto;
import com.sharpon.client.lookup.service.GenderService;
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
class GenderControllerTest {

    @Mock
    GenderService genderService;

    @InjectMocks
    GenderController genderController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        GenderDto dto = GenderDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(genderService.save(any())).willReturn(dto);

        GenderDto response = genderController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(genderService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(genderService).deleteById(anyLong());
        ResponseEntity<Void> response = genderController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<GenderDto> expected = new PageImpl<>(
                Arrays.asList(
                        GenderDto.builder().id(1L).code("001").name("Item 1").build(),
                        GenderDto.builder().id(2L).code("002").name("Item 2").build(),
                        GenderDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(genderService.search(any())).thenReturn(expected);

        ResponseEntity<Page<GenderDto>> response = genderController.search(Pageable.unpaged());
        Page<GenderDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(genderService, times(1)).search(Pageable.unpaged());
    }

}
