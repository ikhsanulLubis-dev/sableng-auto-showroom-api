package com.sablengauto.showroomapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sablengauto.showroomapi.entity.Car;
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

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping
    public Car createCar(@RequestBody @Valid CarRequest request) {
        return carService.createCar(request);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id,
            @RequestBody @Valid CarRequest request) {

        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {

        carService.deleteCar(id);

        return "Car deleted successfully";
    }
}
