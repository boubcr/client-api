package com.sharpon.client.lookup.Service;

import com.sharpon.client.lookup.dto.CategoryDto;
import com.sharpon.client.lookup.entity.Category;
import com.sharpon.client.lookup.mapper.CategoryMapper;
import com.sharpon.client.lookup.repository.CategoryRepository;
import com.sharpon.client.lookup.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository repository;

    @Mock
    CategoryMapper mapper;

    @InjectMocks
    CategoryService service;

    private Category entity;
    private CategoryDto dto;

    @BeforeEach
    public void setUp() {
        dto = CategoryDto.builder()
                .id(1L)
                .code("001")
                .name("Item 1")
                .build();

        entity = new Category();
        BeanUtils.copyProperties(dto, entity);
    }

    @Test
    void findById_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.findById(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void findById_withNotExistingId_throwsEntityNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Executable executable = () -> service.findById(1L);
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void findById_withValidUserId_findsSuccessfully() {
        when(mapper.toDto(entity)).thenReturn(dto);
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        CategoryDto actual = service.findById(1L);
        assertEquals(dto, actual);
    }

    @Test
    void delete_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.deleteById(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void delete_withNotExistingId_throwsEntityNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Executable executable = () -> service.deleteById(1L);
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void delete_withValidUserId_deletesSuccessfully() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(any(Category.class));
        service.deleteById(1L);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void save_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.save(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void save_withValidInput_savesSuccessfully() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);
        when(repository.save(any(Category.class))).thenReturn(entity);
        CategoryDto response = service.save(dto);

        assertEquals(dto, response);
        verify(repository, times(1)).save(any(Category.class));
    }

    @Test
    void search_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.search(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void search_withValidInput_shouldReturnList() {
        Page<Category> expected = new PageImpl<>(Collections.singletonList(entity));

        when(mapper.toDto(any(Category.class))).thenReturn(dto);
        when(repository.findAll(any(Pageable.class))).thenReturn(expected);

        Page<CategoryDto> response = service.search(Pageable.unpaged());

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(expected.getSize(), response.getSize())
        );
    }

}
