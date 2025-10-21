package com.example.HPPO_Backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping("/uploads")
public class UploadController {

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("files") MultipartFile[] files) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            List<String> urls = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String ext = Optional.ofNullable(file.getOriginalFilename())
                        .filter(fn -> fn.contains("."))
                        .map(fn -> fn.substring(fn.lastIndexOf(".")))
                        .orElse("");

                String filename = UUID.randomUUID().toString().replace("-", "") + ext;
                Path target = Paths.get(UPLOAD_DIR, filename);
                Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

                String publicUrl = "/uploads/" + filename; // lo servimos estático (ver config abajo)
                urls.add(publicUrl);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("urls", urls);
            return ResponseEntity.ok(body);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error subiendo archivos"));
        }
    }
}