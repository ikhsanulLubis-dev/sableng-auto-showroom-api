package com.sablengauto.showroomapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sablengauto.showroomapi.service.CarImageService;

@RestController
@RequestMapping("/api/cars")
public class CarImageController {

    private final CarImageService carImageService;

    public CarImageController(
            CarImageService carImageService) {

        this.carImageService = carImageService;

    }

    @PostMapping("/{id}/images")
    public ResponseEntity<String> uploadImage(

            @PathVariable Long id,

            @RequestParam("file") MultipartFile file,

            @RequestParam String uploadedBy)

            throws IOException {

        carImageService.uploadCarImage(
                id,
                file,
                uploadedBy);

        return ResponseEntity.ok(
                "Image uploaded successfully");

    }
}
