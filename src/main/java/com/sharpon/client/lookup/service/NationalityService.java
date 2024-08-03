package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.NationalityDto;
import com.sharpon.client.lookup.mapper.NationalityMapper;
import com.sharpon.client.lookup.repository.NationalityRepository;
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
public class NationalityService {
    private final NationalityRepository nationalityRepository;
    private final NationalityMapper nationalityMapper;

    public Page<NationalityDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return nationalityRepository.findAll(pageable).map(nationalityMapper::toDto);
    }

    public NationalityDto save(NationalityDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Lookup object");
        }

        return nationalityMapper.toDto(nationalityRepository.save(nationalityMapper.toEntity(dto)));
    }

    public NationalityDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Id");
        }

        return nationalityRepository.findById(id)
                .map(nationalityMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> nationalityRepository.findById(itemId)
                        .ifPresentOrElse(nationalityRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
