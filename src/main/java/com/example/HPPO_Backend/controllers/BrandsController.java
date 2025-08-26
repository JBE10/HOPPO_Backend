package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Brand;
import com.example.HPPO_Backend.entity.dto.BrandRequest;
import com.example.HPPO_Backend.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("brands")
public class BrandsController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getBrands() {
        return ResponseEntity.ok(this.brandService.getBrands());
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
