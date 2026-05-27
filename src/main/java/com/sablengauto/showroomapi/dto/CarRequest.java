package com.sablengauto.showroomapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequest {

    @NotBlank(message = "Brand Mobil gak boleh kosong")
    private String brand;

    @NotBlank(message = "Model Mobil gak boleh kosong")
    private String model;
    
    @Min(value = 2000, message = "Tahun Mobil harus 2000 atau lebih")
    private int year;
    
    @Min(value = 0, message = "Harga harus bernilai positif")
    private double price;
}
