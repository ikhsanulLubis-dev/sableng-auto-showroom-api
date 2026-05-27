package com.sablengauto.showroomapi.repository;

import com.sablengauto.showroomapi.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
