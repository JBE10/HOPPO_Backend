package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Brand;
import com.example.HPPO_Backend.entity.dto.BrandRequest;
import com.example.HPPO_Backend.exceptions.BrandDuplicateException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface BrandService {
    Page<Brand> getBrands(PageRequest pageRequest);
    Optional<Brand> getBrandById(Long brandId);
    Brand createBrand(BrandRequest brandRequest) throws BrandDuplicateException;
}
