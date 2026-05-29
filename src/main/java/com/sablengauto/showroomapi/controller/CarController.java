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
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import io.swagger.v3.oas.annotations.Parameter;


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

    @GetMapping
    public Page<Car> getAllCars(

            @RequestParam(required = false) String brand,

            @RequestParam(required = false) CarStatus status,

            @RequestParam(required = false) Integer year,

            @RequestParam(required = false) Double minPrice,

            @RequestParam(required = false) Double maxPrice,

            @RequestParam(required = false) Integer startYear,

            @RequestParam(required = false) Integer endYear,

            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @Parameter(hidden = true) Pageable pageable) {

        log.info(
                "GET cars request received - brand: {}, status: {}, year: {}",
                brand,
                status,
                year);
        appLoggerService.log("GET",
                "/api/cars?brand=" + brand + "&status=" + status + "&year=" + year + "&minPrice=" + minPrice
                        + "&maxPrice=" + maxPrice + "&startYear=" + startYear + "&endYear=" + endYear + "&page="
                        + pageable.getPageNumber()
                        + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort() + "&unpaged="
                        + pageable.isUnpaged() + "&paged=" + pageable.isPaged(),
                "GET cars request received - brand: " + brand + ", status: " + status + ", year: " + year
                        + ",minPrice: "
                        + minPrice + ", maxPrice: " + maxPrice + ", startYear: " + startYear + ", endYear: " + endYear
                        + " and pagination: " + pageable);

        return carService.filterCars(
                brand,
                status,
                year,
                minPrice,
                maxPrice,
                startYear,
                endYear,
                pageable);

    }

    @PostMapping
    public Car createCar(@RequestBody @Valid CarRequest request) {
        appLoggerService.log("POST", "/api/cars", "CREATE car request received");
        return carService.createCar(request);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id,
            @RequestBody @Valid CarRequest request) throws BadRequestException {
        appLoggerService.log("PUT", "/api/cars/" + id,
                "UPDATE car request received" + " with id: " + id + " and request body: brand :" + request.getBrand()
                        + ", model:" + request.getModel() + ", year:" + request.getYear() + ", price:"
                        + request.getPrice() + ", status:" + request.getStatus());

        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        appLoggerService.log("DELETE", "/api/cars/" + id, "DELETE car request received");
        return "Car deleted successfully";
    }
}
