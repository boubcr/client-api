package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.CountryDto;
import com.sharpon.client.lookup.service.CountryService;
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

import static com.sharpon.client.lookup.controller.CountryController.BASE_URL;


@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Country API")
public class CountryController {
    public static final String BASE_URL = "/api/v1/country";
    private final CountryService countryService;

    @PostMapping("/save")
    public CountryDto save(@Validated @RequestBody CountryDto dto) {
        return countryService.save(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        countryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CountryDto>> search(@PageableDefault(size = 20, sort = {"updatedAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(countryService.search(pageable));
    }
}
