package com.sablengauto.showroomapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sablengauto.showroomapi.entity.Car;
import com.sablengauto.showroomapi.repository.CarRepository;
import com.sablengauto.showroomapi.dto.CarRequest;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car createCar(CarRequest request) {

        Car car = new Car();
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setPrice(request.getPrice());

        return carRepository.save(car);
    }

    public Car updateCar(Long id, CarRequest request) {

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setPrice(request.getPrice());

        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
