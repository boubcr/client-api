package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.CategoryDto;
import com.sharpon.client.lookup.mapper.CategoryMapper;
import com.sharpon.client.lookup.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Page<CategoryDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    public CategoryDto save(CategoryDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid input");
        }

        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(dto)));
    }

    public CategoryDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid input");
        }

        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> categoryRepository.findById(itemId)
                        .ifPresentOrElse(categoryRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
