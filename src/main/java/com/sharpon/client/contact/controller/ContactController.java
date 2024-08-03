package com.sharpon.client.contact.controller;

import com.sharpon.client.address.dto.AddressCriteria;
import com.sharpon.client.address.dto.AddressDto;
import com.sharpon.client.address.service.AddressService;
import com.sharpon.client.contact.dto.ContactCriteria;
import com.sharpon.client.contact.dto.ContactDto;
import com.sharpon.client.contact.service.ContactService;
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

import static com.sharpon.client.contact.controller.ContactController.BASE_URL;


@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Contact API")
public class ContactController {

    public static final String BASE_URL = "/api/v1/contact";
    private final ContactService contactService;

    @GetMapping("/search")
    public ResponseEntity<Page<ContactDto>> search(@Validated ContactCriteria criteria, @PageableDefault(size = 20, sort = {"updatedAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(contactService.search(criteria, pageable));
    }

    @PostMapping("/create/{clientId}")
    public ContactDto create(@Validated @RequestBody ContactDto dto, @PathVariable("clientId") Long clientId) {
        return contactService.create(dto, clientId);
    }

    @PutMapping("/update")
    public ContactDto update(@Validated  @RequestBody ContactDto dto) {
        return contactService.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        contactService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
