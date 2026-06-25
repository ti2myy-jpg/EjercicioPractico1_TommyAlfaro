package com.tommy.gimnasiofitness.repository;

import com.tommy.gimnasiofitness.domain.ReservacionClase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservacionClaseRepository extends JpaRepository<ReservacionClase, Integer> {

    List<ReservacionClase> findByServicioId(Integer servicioId);
}
