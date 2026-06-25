package com.tommy.gimnasiofitness.repository;

import com.tommy.gimnasiofitness.domain.ClaseEntrenamiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaseEntrenamientoRepository extends JpaRepository<ClaseEntrenamiento, Integer> {

    List<ClaseEntrenamiento> findByCategoriaId(Integer categoriaId);
}
