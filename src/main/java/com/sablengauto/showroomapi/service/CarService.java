package com.sablengauto.showroomapi.service;

import java.util.List;

import com.sablengauto.showroomapi.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sablengauto.showroomapi.entity.Car;
import com.sablengauto.showroomapi.enums.CarStatus;
import com.sablengauto.showroomapi.repository.CarRepository;
import com.sablengauto.showroomapi.dto.CarRequest;
import com.sablengauto.showroomapi.exception.ResourceNotFoundException;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // public List<Car> getAllCars() {
    // return carRepository.findAll();
    // }

    public Page<Car> getAllCars(Pageable pageable) {

        return carRepository.findAll(pageable);

    }

    public Page<Car> searchCars(
            String brand,
            Pageable pageable) {

        return carRepository
                .findByBrandContainingIgnoreCase(
                        brand,
                        pageable);

    }

    public Page<Car> getCarsByStatus(
            CarStatus status,
            Pageable pageable) {

        return carRepository.findByStatus(
                status,
                pageable);

    }

    public Page<Car> getCarsByYear(
            Integer year,
            Pageable pageable) {

        return carRepository.findByYear(
                year,
                pageable);

    }

    public Car createCar(CarRequest request) {

        Car car = new Car();
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setPrice(request.getPrice());
        car.setStatus(request.getStatus());

        return carRepository.save(car);
    }

    public Car updateCar(Long id, CarRequest request) throws BadRequestException {

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mobil gak ditemukan"));

        if (request.getStatus() == CarStatus.SOLD_OUT) {
            throw new BadRequestException("Status  SOLD_OUT tidak bisa di update");
        } else {
            car.setBrand(request.getBrand());
            car.setModel(request.getModel());
            car.setYear(request.getYear());
            car.setPrice(request.getPrice());
            car.setStatus(request.getStatus());

            return carRepository.save(car);

        }

    }

    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mobil gak ditemukan"));
        carRepository.deleteById(id);
    }
}
