package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.CompanyDto;
import com.sharpon.client.lookup.mapper.CompanyMapper;
import com.sharpon.client.lookup.repository.CompanyRepository;
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
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public Page<CompanyDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return companyRepository.findAll(pageable).map(companyMapper::toDto);
    }

    public CompanyDto save(CompanyDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Lookup object");
        }

        return companyMapper.toDto(companyRepository.save(companyMapper.toEntity(dto)));
    }

    public CompanyDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Id");
        }

        return companyRepository.findById(id)
                .map(companyMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> companyRepository.findById(itemId)
                        .ifPresentOrElse(companyRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
