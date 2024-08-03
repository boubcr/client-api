package com.sharpon.client.client.service;

import com.sharpon.client.client.dto.ClientCriteria;
import com.sharpon.client.client.dto.ClientDto;
import com.sharpon.client.client.entity.Client;
import com.sharpon.client.client.mapper.ClientMapper;
import com.sharpon.client.client.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public Page<ClientDto> search(ClientCriteria criteria, Pageable pageable) {
        Pageable pageRequest = Optional.ofNullable(pageable).orElse(Pageable.unpaged());
        if (Objects.isNull(criteria)) {
            return clientRepository.findAll(pageRequest)
                    .map(clientMapper::toDto);
        }

        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        return clientRepository.findAll(Example.of(clientMapper.toEntity(criteria), matcher), pageRequest)
                .map(clientMapper::toDto);
    }

    public ClientDto create(ClientDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Client object");
        }

        return clientMapper.toDto(clientRepository.save(clientMapper.toEntity(dto)));
    }

    public ClientDto update(ClientDto dto) {
        if (Objects.isNull(dto) || Objects.isNull(dto.getId())) {
            throw new IllegalArgumentException("Invalid Client Id");
        }

        Client entity = clientRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        Client partner = clientMapper.partialUpdate(dto, entity);
        return clientMapper.toDto(clientRepository.save(partner));
    }

    public ClientDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Client Id");
        }

        return clientRepository.findById(id)
                .map(clientMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(userId -> clientRepository.findById(userId)
                        .ifPresentOrElse(clientRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
