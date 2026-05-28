package com.sablengauto.showroomapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sablengauto.showroomapi.entity.CarImage;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
}
