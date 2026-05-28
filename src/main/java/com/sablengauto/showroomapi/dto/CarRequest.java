package com.sablengauto.showroomapi.dto;

import com.sablengauto.showroomapi.enums.CarStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "Harga wajib diisi")
    @Positive(message = "Harga harus lebih besar dari 0")
    private Double price;

    @NotNull(message = "Status is required")
    private CarStatus status;
}
