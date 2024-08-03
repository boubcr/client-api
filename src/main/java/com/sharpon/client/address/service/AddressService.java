package com.sharpon.client.address.service;

import com.sharpon.client.address.dto.AddressCriteria;
import com.sharpon.client.address.dto.AddressDto;
import com.sharpon.client.address.entity.Address;
import com.sharpon.client.address.mapper.AddressMapper;
import com.sharpon.client.address.repository.AddressRepository;
import com.sharpon.client.client.service.ClientService;
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
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ClientService clientService;

    public Page<AddressDto> search(AddressCriteria criteria, Pageable pageable) {
        Pageable pageRequest = Optional.ofNullable(pageable).orElse(Pageable.unpaged());
        if (Objects.isNull(criteria)) {
            return addressRepository.findAll(pageRequest)
                    .map(addressMapper::toDto);
        }

        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        return addressRepository.findAll(Example.of(addressMapper.toEntity(criteria), matcher), pageRequest)
                .map(addressMapper::toDto);
    }

    public AddressDto create(AddressDto dto, Long clientId) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Invalid Address object");
        }

        if (Objects.isNull(clientId)) {
            throw new IllegalArgumentException("Invalid Client Id");
        }

        return addressMapper.toDto(addressRepository.save(addressMapper.toEntity(dto)));
    }

    public AddressDto update(AddressDto dto) {
        if (Objects.isNull(dto) || Objects.isNull(dto.getId())) {
            throw new IllegalArgumentException("Invalid Address");
        }

        Address entity = addressRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        Address partner = addressMapper.partialUpdate(dto, entity);
        return addressMapper.toDto(addressRepository.save(partner));
    }

    public AddressDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Invalid Address Id");
        }

        return addressRepository.findById(id)
                .map(addressMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id) {
        Optional.ofNullable(id).ifPresentOrElse(userId -> addressRepository.findById(userId)
                        .ifPresentOrElse(addressRepository::delete, () -> {
                            throw new EntityNotFoundException();
                        }),
                () -> {
                    throw new IllegalArgumentException();
                });
    }
}
