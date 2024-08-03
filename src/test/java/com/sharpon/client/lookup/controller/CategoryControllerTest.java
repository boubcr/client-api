package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.CategoryDto;
import com.sharpon.client.lookup.service.CategoryService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    @Test
    void save_withValidInputs_shouldSaveSuccessfully() {
        CategoryDto dto = CategoryDto.builder().id(1L).version(0L).code("001").name("Item 1").build();
        given(categoryService.save(any())).willReturn(dto);

        CategoryDto response = categoryController.save(dto);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(dto, response)
        );

        verify(categoryService, times(1)).save(dto);
    }

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(categoryService).deleteById(anyLong());
        ResponseEntity<Void> response = categoryController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<CategoryDto> expected = new PageImpl<>(
                Arrays.asList(
                        CategoryDto.builder().id(1L).code("001").name("Item 1").build(),
                        CategoryDto.builder().id(2L).code("002").name("Item 2").build(),
                        CategoryDto.builder().id(3L).code("003").name("Item 3").build()
                )
        );

        when(categoryService.search(any())).thenReturn(expected);

        ResponseEntity<Page<CategoryDto>> response = categoryController.search(Pageable.unpaged());
        Page<CategoryDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(categoryService, times(1)).search(Pageable.unpaged());
    }

}
