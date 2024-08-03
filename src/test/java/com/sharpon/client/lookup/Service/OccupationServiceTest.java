package com.sharpon.client.lookup.Service;

import com.sharpon.client.lookup.dto.OccupationDto;
import com.sharpon.client.lookup.entity.Occupation;
import com.sharpon.client.lookup.mapper.OccupationMapper;
import com.sharpon.client.lookup.repository.OccupationRepository;
import com.sharpon.client.lookup.service.OccupationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OccupationServiceTest {
    @Mock
    OccupationRepository repository;

    @Mock
    OccupationMapper mapper;

    @InjectMocks
    OccupationService service;

    private Occupation entity;
    private OccupationDto dto;

    @BeforeEach
    public void setUp() {
        dto = OccupationDto.builder()
                .id(1L)
                .code("001")
                .name("Item 1")
                .build();

        entity = new Occupation();
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
        OccupationDto actual = service.findById(1L);
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
        doNothing().when(repository).delete(any(Occupation.class));
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
        when(repository.save(any(Occupation.class))).thenReturn(entity);
        OccupationDto response = service.save(dto);

        assertEquals(dto, response);
        verify(repository, times(1)).save(any(Occupation.class));
    }

    @Test
    void search_withNullInput_throwsIllegalArgumentException() {
        Executable executable = () -> service.search(null);
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void search_withValidInput_shouldReturnList() {
        Page<Occupation> expected = new PageImpl<>(Collections.singletonList(entity));

        when(mapper.toDto(any(Occupation.class))).thenReturn(dto);
        when(repository.findAll(any(Pageable.class))).thenReturn(expected);

        Page<OccupationDto> response = service.search(Pageable.unpaged());

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(expected.getSize(), response.getSize())
        );
    }

}
