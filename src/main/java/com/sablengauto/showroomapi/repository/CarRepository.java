package com.sablengauto.showroomapi.repository;

import com.sablengauto.showroomapi.entity.Car;
import com.sablengauto.showroomapi.enums.CarStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByBrandContainingIgnoreCase(
            String brand,
            Pageable pageable);

    Page<Car> findByStatus(
            CarStatus status,
            Pageable pageable);

    Page<Car> findByYear(
            Integer year,
            Pageable pageable);
}
