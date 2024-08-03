package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.CityDto;
import com.sharpon.client.lookup.service.CityService;
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

import static com.sharpon.client.lookup.controller.CityController.BASE_URL;


@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Tag(name = "City API")
public class CityController {
    public static final String BASE_URL = "/api/v1/city";
    private final CityService cityService;

    @PostMapping("/save")
    public CityDto save(@Validated @RequestBody CityDto dto) {
        return cityService.save(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        cityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityDto>> search(@PageableDefault(size = 20, sort = {"updatedAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(cityService.search(pageable));
    }
}
