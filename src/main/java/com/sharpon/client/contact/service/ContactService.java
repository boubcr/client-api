package com.sharpon.client.contact.service;

import com.sharpon.client.contact.dto.ContactCriteria;
import com.sharpon.client.contact.dto.ContactDto;
import com.sharpon.client.contact.entity.Contact;
import com.sharpon.client.contact.mapper.ContactMapper;
import com.sharpon.client.contact.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public Page<ContactDto> search(ContactCriteria criteria, Pageable pageable) {
        Pageable pageRequest = Optional.ofNullable(pageable).orElse(Pageable.unpaged());
        if (Objects.isNull(criteria)) {
            return contactRepository.findAll(pageRequest)
                    .map(contactMapper::toDto);
        }

        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        return contactRepository.findAll(Example.of(contactMapper.toEntity(criteria), matcher), pageRequest)
                .map(contactMapper::toDto);
    }

    public ContactDto create(ContactDto dto, Long clientId) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Contact object");
        }

        if (Objects.isNull(clientId)) {
            throw new IllegalArgumentException("Invalid Client Id");
        }

        return contactMapper.toDto(contactRepository.save(contactMapper.toEntity(dto)));
    }

    public ContactDto update(ContactDto dto) {
        if (Objects.isNull(dto) || Objects.isNull(dto.getId())) {
            throw new IllegalArgumentException("Invalid Contact Id");
        }

        Contact entity = contactRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        Contact partner = contactMapper.partialUpdate(dto, entity);
        return contactMapper.toDto(contactRepository.save(partner));
    }

    public ContactDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Contact Id");
        }

        return contactRepository.findById(id)
                .map(contactMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(userId -> contactRepository.findById(userId)
                        .ifPresentOrElse(contactRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
