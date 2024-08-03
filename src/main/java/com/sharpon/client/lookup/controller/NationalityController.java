package com.sharpon.client.lookup.controller;

import com.sharpon.client.lookup.dto.NationalityDto;
import com.sharpon.client.lookup.service.NationalityService;
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

import static com.sharpon.client.lookup.controller.NationalityController.BASE_URL;


@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Nationality API")
public class NationalityController {
    public static final String BASE_URL = "/api/v1/nationality";
    private final NationalityService nationalityService;

    @PostMapping("/save")
    public NationalityDto save(@Validated @RequestBody NationalityDto dto) {
        return nationalityService.save(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        nationalityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NationalityDto>> search(@PageableDefault(size = 20, sort = {"updatedAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(nationalityService.search(pageable));
    }
}
