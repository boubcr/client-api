package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.OccupationDto;
import com.sharpon.client.lookup.service.OccupationService;
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
class OccupationControllerTest {

    @Mock
    OccupationService occupationService;

    @InjectMocks
    OccupationController occupationController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        OccupationDto dto = OccupationDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(occupationService.save(any())).willReturn(dto);

        OccupationDto response = occupationController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(occupationService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(occupationService).deleteById(anyLong());
        ResponseEntity<Void> response = occupationController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<OccupationDto> expected = new PageImpl<>(
                Arrays.asList(
                        OccupationDto.builder().id(1L).code("001").name("Item 1").build(),
                        OccupationDto.builder().id(2L).code("002").name("Item 2").build(),
                        OccupationDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(occupationService.search(any())).thenReturn(expected);

        ResponseEntity<Page<OccupationDto>> response = occupationController.search(Pageable.unpaged());
        Page<OccupationDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(occupationService, times(1)).search(Pageable.unpaged());
    }

}
