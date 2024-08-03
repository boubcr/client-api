package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.CountryDto;
import com.sharpon.client.lookup.mapper.CountryMapper;
import com.sharpon.client.lookup.repository.CountryRepository;
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
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public Page<CountryDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return countryRepository.findAll(pageable).map(countryMapper::toDto);
    }

    public CountryDto save(CountryDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Lookup object");
        }

        return countryMapper.toDto(countryRepository.save(countryMapper.toEntity(dto)));
    }

    public CountryDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Id");
        }

        return countryRepository.findById(id)
                .map(countryMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> countryRepository.findById(itemId)
                        .ifPresentOrElse(countryRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
