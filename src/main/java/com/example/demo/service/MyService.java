package com.example.demo.service;

import com.example.demo.controller.dto.CrearPrestamoDTO;
import com.example.demo.model.EstadoPrestamo;
import com.example.demo.model.Prestamo;
import com.example.demo.model.Videojuego;
import com.example.demo.repository.PrestamoRepository;
import com.example.demo.repository.VideojuegoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MyService {
   private final PrestamoRepository prestamoRepository;
   private final VideojuegoRepository videojuegoRepository;

    public MyService(PrestamoRepository prestamoRepository, VideojuegoRepository videojuegoRepository) {
        this.prestamoRepository = prestamoRepository;
        this.videojuegoRepository = videojuegoRepository;
    }


    @Transactional
    public Prestamo crearPrestamo(CrearPrestamoDTO payload){
        Videojuego videojuego = videojuegoRepository.findById(payload.getIdVideojuego())
               .orElseThrow(
                       () -> new RuntimeException("Videojuego no existe")
               );

        if(!videojuego.isDisponible()){
            throw new RuntimeException("El videojuego no esta disponible");
        }



        videojuego.setDisponible(false);
        videojuegoRepository.save(videojuego); // true -> false

        Prestamo prestamo = new Prestamo();
        prestamo.setNombreSolicitante(payload.getNombreSolicitante());
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(null);
        prestamo.setEstado(EstadoPrestamo.ACTIVO);
        prestamo.setVideojuego(videojuego);


       return prestamoRepository.save(prestamo);
    }


    @Transactional
    public Prestamo cancelarPrestamo(Long id){
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("El prestamo no existe")
                );

        if (prestamo.getEstado() != EstadoPrestamo.ACTIVO){
            throw new RuntimeException("Solo se pueden cancelar prestamos activos");
        }

        // asignarle el nuevo estado de cancelado
        prestamo.setEstado(EstadoPrestamo.CANCELADO);

        // acceder al videojuego asociado, y cambiar su estado a true
        prestamo.getVideojuego().setDisponible(true);

        // actualizar el videojuego
        videojuegoRepository.save(prestamo.getVideojuego());

        //
        return prestamoRepository.save(prestamo);
    }


    @Transactional
    public void cancelarTodosPrestamos(){
        // consultar prestamos activos por medio de una query method
        List<Prestamo> prestamos = prestamoRepository
                .findAllByEstado(EstadoPrestamo.ACTIVO);

        // iterar la lista de prestamos
        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo prestamo = prestamos.get(i);

            // cambiar estados
            prestamo.setEstado(EstadoPrestamo.CANCELADO);
            prestamo.getVideojuego().setDisponible(true);

            // guardar informacion
            videojuegoRepository.save(prestamo.getVideojuego());
            prestamoRepository.save(prestamo);
        }

    }

}
