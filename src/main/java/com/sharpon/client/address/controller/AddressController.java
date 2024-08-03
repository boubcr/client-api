package com.sharpon.client.address.controller;

import com.sharpon.client.address.dto.AddressCriteria;
import com.sharpon.client.address.dto.AddressDto;
import com.sharpon.client.address.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.sharpon.client.address.controller.AddressController.BASE_URL;


@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Address API")
public class AddressController {
    public static final String BASE_URL = "/api/v1/address";
    private final AddressService addressService;

    @GetMapping("/search")
    public ResponseEntity<Page<AddressDto>> search(@Validated AddressCriteria criteria, @PageableDefault(size = 20, sort = {"updatedAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(addressService.search(criteria, pageable));
    }

    @PostMapping("/create/{clientId}")
    public AddressDto create(@Validated @RequestBody AddressDto dto, @PathVariable("clientId") Long clientId) {
        return addressService.create(dto, clientId);
    }

    @PutMapping("/update")
    public AddressDto update(@Validated  @RequestBody AddressDto dto) {
        return addressService.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        addressService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
