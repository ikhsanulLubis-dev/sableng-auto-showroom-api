package com.sablengauto.showroomapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sablengauto.showroomapi.entity.CarImage;
import com.sablengauto.showroomapi.exception.BadRequestException;
import com.sablengauto.showroomapi.service.CarImageService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/cars")
public class CarImageController {

    private final CarImageService carImageService;

    public CarImageController(
            CarImageService carImageService) {

        this.carImageService = carImageService;

    }

    @PostMapping(value = "/{id}/images", consumes = "multipart/form-data")
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

    @GetMapping("/{id}/images")
    public ResponseEntity<List<CarImage>> getCarImages(

            @PathVariable Long id) {

        return ResponseEntity.ok(
                carImageService
                        .getImagesByCarId(id));

    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String fileName)
            throws IOException {

        Path path = Paths.get(
                "uploads/").resolve(fileName);

        Resource resource = new UrlResource(
                path.toUri());

        if (!resource.exists()) {

            throw new BadRequestException(
                    "Image tidak ditemukan");

        }

        return ResponseEntity.ok()
                .contentType(
                        MediaType.IMAGE_JPEG)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\""
                                + resource.getFilename()
                                + "\"")
                .body(resource);

    }
}
