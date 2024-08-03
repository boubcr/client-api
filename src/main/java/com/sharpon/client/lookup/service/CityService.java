package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.CityDto;
import com.sharpon.client.lookup.mapper.CityMapper;
import com.sharpon.client.lookup.repository.CityRepository;
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
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public Page<CityDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return cityRepository.findAll(pageable).map(cityMapper::toDto);
    }

    public CityDto save(CityDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Lookup object");
        }

        return cityMapper.toDto(cityRepository.save(cityMapper.toEntity(dto)));
    }

    public CityDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Id");
        }

        return cityRepository.findById(id)
                .map(cityMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> cityRepository.findById(itemId)
                        .ifPresentOrElse(cityRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
