package com.sharpon.client.client.controller;

import com.sharpon.client.client.dto.ClientCriteria;
import com.sharpon.client.client.dto.ClientDto;
import com.sharpon.client.client.service.ClientService;
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

import static com.sharpon.client.client.controller.ClientController.BASE_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Client API")
public class ClientController {
    public static final String BASE_URL = "/api/v1/client";
    private final ClientService clientService;

    @GetMapping("/search")
    public ResponseEntity<Page<ClientDto>> search(ClientCriteria criteria, @PageableDefault(size = 20, sort = {"updatedAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(clientService.search(criteria, pageable));
    }

    @PostMapping("/create")
    public ClientDto create(@Validated @RequestBody ClientDto dto) {
        return clientService.create(dto);
    }

    @PutMapping("/update")
    public ClientDto update(@Validated @RequestBody ClientDto dto) {
        return clientService.update(dto);
    }

    @GetMapping("/{id}")
    public ClientDto findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
