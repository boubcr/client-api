package com.sharpon.client.lookup.service;

import com.sharpon.client.lookup.dto.ContactTypeDto;
import com.sharpon.client.lookup.mapper.ContactTypeMapper;
import com.sharpon.client.lookup.repository.ContactTypeRepository;
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
public class ContactTypeService {
    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;

    public Page<ContactTypeDto> search(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return contactTypeRepository.findAll(pageable).map(contactTypeMapper::toDto);
    }

    public ContactTypeDto save(ContactTypeDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Lookup object");
        }

        return contactTypeMapper.toDto(contactTypeRepository.save(contactTypeMapper.toEntity(dto)));
    }

    public ContactTypeDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Id");
        }

        return contactTypeRepository.findById(id)
                .map(contactTypeMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(itemId -> contactTypeRepository.findById(itemId)
                        .ifPresentOrElse(contactTypeRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
