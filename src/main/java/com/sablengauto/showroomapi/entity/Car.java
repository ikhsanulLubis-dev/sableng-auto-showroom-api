package com.sablengauto.showroomapi.entity;

import com.sablengauto.showroomapi.enums.CarStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private Integer year;
    private Double price;
    @Enumerated(EnumType.STRING)
    private CarStatus status;

}