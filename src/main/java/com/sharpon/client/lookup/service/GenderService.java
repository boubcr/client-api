package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.GenderDto;
import com.sharpon.client.lookup.mapper.GenderMapper;
import com.sharpon.client.lookup.repository.GenderRepository;
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
public class GenderService {
    private final GenderRepository genderRepository;
    private final GenderMapper genderMapper;

    public Page<GenderDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return genderRepository.findAll(pageable).map(genderMapper::toDto);
    }

    public GenderDto save(GenderDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Lookup object");
        }

        return genderMapper.toDto(genderRepository.save(genderMapper.toEntity(dto)));
    }

    public GenderDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Id");
        }

        return genderRepository.findById(id)
                .map(genderMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> genderRepository.findById(itemId)
                        .ifPresentOrElse(genderRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
