package com.sablengauto.showroomapi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sablengauto.showroomapi.entity.Car;
import com.sablengauto.showroomapi.entity.CarImage;
import com.sablengauto.showroomapi.exception.BadRequestException;
import com.sablengauto.showroomapi.repository.CarImageRepository;
import com.sablengauto.showroomapi.repository.CarRepository;

@Service
public class CarImageService {
        private final CarRepository carRepository;
        private final CarImageRepository carImageRepository;
        private final AppLoggerService appLoggerService;

        private final String uploadDir = "uploads/";

        public CarImageService(
                        CarRepository carRepository,
                        CarImageRepository carImageRepository,
                        AppLoggerService appLoggerService) {

                this.carRepository = carRepository;
                this.carImageRepository = carImageRepository;
                this.appLoggerService = appLoggerService;

        }

        public List<CarImage> getImagesByCarId(
                        Long carId) {

                return carImageRepository
                                .findByCarId(carId);

        }

        public void uploadCarImage(
                        Long carId,
                        MultipartFile file,
                        String uploadedBy)
                        throws IOException {

                Car car = carRepository.findById(carId)
                                .orElseThrow(() -> new BadRequestException(
                                                "Mobil tidak ditemukan"));

                if (file.isEmpty()) {

                        throw new BadRequestException(
                                        "File tidak boleh kosong");

                }

                if (!file.getContentType()
                                .startsWith("image/")) {

                        throw new BadRequestException(
                                        "File harus berupa gambar");

                }

                long maxSize = 1024 * 1024;

                if (file.getSize() > maxSize) {

                        throw new BadRequestException(
                                        "Ukuran file maksimal 1MB");

                }

                String fileName = System.currentTimeMillis()
                                + "_"
                                + file.getOriginalFilename();

                Path path = Paths.get(
                                uploadDir + fileName);

                Files.copy(
                                file.getInputStream(),
                                path);

                CarImage carImage = new CarImage();

                carImage.setFileName(fileName);

                carImage.setFilePath(
                                path.toString());

                carImage.setFileSize(
                                file.getSize());

                carImage.setUploadedBy(
                                uploadedBy);

                carImage.setUploadedAt(
                                LocalDateTime.now());

                carImage.setCar(car);

                carImageRepository.save(carImage);

                appLoggerService.log(
                                "UPLOAD",
                                "/api/cars/" + carId + "/images",
                                "Image uploaded: " + fileName);

        }
}
