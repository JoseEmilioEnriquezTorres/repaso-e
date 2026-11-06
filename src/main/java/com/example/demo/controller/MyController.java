package com.example.demo.controller;

import com.example.demo.controller.dto.CrearPrestamoDTO;
import com.example.demo.model.Prestamo;
import com.example.demo.service.MyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controlador")
@CrossOrigin("*")
public class MyController {
    private final MyService servicie;

    public MyController(MyService servicie) {
        this.servicie = servicie;
    }


    @PostMapping("/registrar-prestamo")
    public ResponseEntity<Prestamo> crearPrestamo
            (@RequestBody @Valid CrearPrestamoDTO payload){

        return ResponseEntity
                .status(201)
                .body(
                        servicie.crearPrestamo(payload)
                );
    }

    @PatchMapping("/cancelar-prestamo/{id}")
    public ResponseEntity<Prestamo> cancelarPrestamo
            (@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(servicie.cancelarPrestamo(id));
    }

    @PatchMapping("/cancelar-todos")
    public ResponseEntity<Object> cancelartodosPrestamos(){
        servicie.cancelarTodosPrestamos();
        return ResponseEntity.status(200).body("Se eliminaron correctamente");
    }
}
