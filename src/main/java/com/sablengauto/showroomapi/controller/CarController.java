package com.sablengauto.showroomapi.controller;

import com.sablengauto.showroomapi.repository.AppLogRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sablengauto.showroomapi.entity.Car;
import com.sablengauto.showroomapi.enums.CarStatus;
import com.sablengauto.showroomapi.service.AppLoggerService;
import com.sablengauto.showroomapi.service.CarService;

import com.sablengauto.showroomapi.dto.CarRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final AppLogRepository appLogRepository;
    private static final Logger log = LoggerFactory.getLogger(CarController.class);
    private final AppLoggerService appLoggerService;
    private final CarService carService;

    public CarController(AppLoggerService appLoggerService, CarService carService, AppLogRepository appLogRepository) {
        this.appLoggerService = appLoggerService;
        this.carService = carService;
        this.appLogRepository = appLogRepository;
    }

    // @GetMapping
    // public List<Car> getAllCars() {
    // // log.info("GET all cars request received");
    // appLoggerService.log("GET", "/api/cars", "GET all cars request received");
    // return carService.getAllCars();
    // }

    // @GetMapping
    // public Page<Car> getAllCars(Pageable pageable) {

    // log.info("GET all cars request received");
    // appLoggerService.log("GET", "/api/cars", "GET all cars request received");
    // return carService.getAllCars(pageable);

    // }

    @GetMapping
    public Page<Car> getAllCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) CarStatus status,
            @RequestParam(required = false) Integer year,
            Pageable pageable) {

        log.info("GET cars request received");

        if (brand != null) {
            log.info("GET cars by brand request received with brand: {} and pagination: {}", brand, pageable);
            appLoggerService.log("GET", "/api/cars?brand=" + brand + "&page=" + pageable.getPageNumber() + "&size="
                    + pageable.getPageSize() + "&sort=" + pageable.getSort() + "&unpaged=" + pageable.isUnpaged()
                    + "&paged=" + pageable.isPaged(),
                    "GET cars by brand request received with brand: " + brand + " and pagination: " + pageable);
            return carService
                    .searchCars(brand, pageable);

        }
        if (status != null) {
            log.info("GET cars by status request received with status: {} and pagination: {}", status, pageable);
            appLoggerService.log("GET", "/api/cars?status=" + status + "&page=" + pageable.getPageNumber() + "&size="
                    + pageable.getPageSize() + "&sort=" + pageable.getSort() + "&unpaged=" + pageable.isUnpaged()
                    + "&paged=" + pageable.isPaged(),
                    "GET cars by status request received with status: " + status + " and pagination: " + pageable);
            return carService
                    .getCarsByStatus(status, pageable);
        }
        if (year != null) {
            log.info("GET cars by year request received with year: {} and pagination: {}", year, pageable);
            appLoggerService.log("GET", "/api/cars?year=" + year + "&page=" + pageable.getPageNumber() + "&size="
                    + pageable.getPageSize() + "&sort=" + pageable.getSort() + "&unpaged=" + pageable.isUnpaged()
                    + "&paged=" + pageable.isPaged(),
                    "GET cars by year request received with year: " + year + " and pagination: " + pageable);
            return carService
                    .getCarsByYear(year, pageable);
        }
        log.info("GET all cars request received");
        appLoggerService.log("GET", "/api/cars", "GET all cars request received");
        return carService.getAllCars(pageable);

    }

    @PostMapping
    public Car createCar(@RequestBody @Valid CarRequest request) {
        appLoggerService.log("POST", "/api/cars", "CREATE car request received");
        return carService.createCar(request);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id,
            @RequestBody @Valid CarRequest request) throws BadRequestException {
        appLoggerService.log("PUT", "/api/cars/" + id, "UPDATE car request received");
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        appLoggerService.log("DELETE", "/api/cars/" + id, "DELETE car request received");
        return "Car deleted successfully";
    }
}
