package com.sablengauto.showroomapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequest {

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;
    
    @Min(value = 2000, message = "Year must be above 2000")
    private int year;
    
    @Min(value = 0, message = "Price must be a positive value")
    private double price;
}
