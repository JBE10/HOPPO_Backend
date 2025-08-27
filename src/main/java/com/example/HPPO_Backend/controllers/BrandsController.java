package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Brand;
import com.example.HPPO_Backend.entity.dto.BrandRequest;
import com.example.HPPO_Backend.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("brands")
public class BrandsController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<Page<Brand>> getBrands(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(this.brandService.getBrands(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(this.brandService.getBrands(PageRequest.of(page, size)));
    }

    @GetMapping({"/{brandId}"})
    public ResponseEntity<Brand> getBrandById(@PathVariable Long brandId) {
        Optional<Brand> result = this.brandService.getBrandById(brandId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createBrand(@RequestBody BrandRequest brandRequest) throws Exception {
        Brand result = this.brandService.createBrand(brandRequest);
        return ResponseEntity.created(URI.create("/brands/" + result.getId())).body(result);
    }
}
