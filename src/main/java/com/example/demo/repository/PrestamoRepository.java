package com.example.demo.repository;

import com.example.demo.model.EstadoPrestamo;
import com.example.demo.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findAllByEstado(EstadoPrestamo estado);
}
