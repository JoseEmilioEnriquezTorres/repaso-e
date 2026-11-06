package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearPrestamoDTO {
    @NotNull(message = "idVideojuego no debe estar vacio prro")
    private Long idVideojuego;
    @NotBlank
    private String nombreSolicitante;
}
