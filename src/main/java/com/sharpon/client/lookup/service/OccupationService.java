package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.OccupationDto;
import com.sharpon.client.lookup.mapper.OccupationMapper;
import com.sharpon.client.lookup.repository.OccupationRepository;
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
public class OccupationService {
    private final OccupationRepository occupationRepository;
    private final OccupationMapper occupationMapper;

    public Page<OccupationDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return occupationRepository.findAll(pageable).map(occupationMapper::toDto);
    }

    public OccupationDto save(OccupationDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Lookup object");
        }

        return occupationMapper.toDto(occupationRepository.save(occupationMapper.toEntity(dto)));
    }

    public OccupationDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Id");
        }

        return occupationRepository.findById(id)
                .map(occupationMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> occupationRepository.findById(itemId)
                        .ifPresentOrElse(occupationRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
