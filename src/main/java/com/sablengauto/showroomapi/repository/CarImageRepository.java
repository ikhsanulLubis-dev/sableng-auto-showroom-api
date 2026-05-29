package com.sablengauto.showroomapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sablengauto.showroomapi.entity.CarImage;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
    List<CarImage> findByCarId(Long carId);
}
