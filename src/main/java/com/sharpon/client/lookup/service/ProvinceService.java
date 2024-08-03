package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.ProvinceDto;
import com.sharpon.client.lookup.mapper.ProvinceMapper;
import com.sharpon.client.lookup.repository.ProvinceRepository;
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
public class ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    public Page<ProvinceDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return provinceRepository.findAll(pageable).map(provinceMapper::toDto);
    }

    public ProvinceDto save(ProvinceDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Lookup object");
        }

        return provinceMapper.toDto(provinceRepository.save(provinceMapper.toEntity(dto)));
    }

    public ProvinceDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Id");
        }

        return provinceRepository.findById(id)
                .map(provinceMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> provinceRepository.findById(itemId)
                        .ifPresentOrElse(provinceRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
