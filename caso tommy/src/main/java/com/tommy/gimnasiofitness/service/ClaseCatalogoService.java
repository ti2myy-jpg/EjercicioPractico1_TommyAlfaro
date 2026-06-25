package com.tommy.gimnasiofitness.service;

import com.tommy.gimnasiofitness.domain.ClaseEntrenamiento;
import java.util.List;
import java.util.Optional;

public interface ClaseCatalogoService {

    List<ClaseEntrenamiento> consultarSesiones();

    Optional<ClaseEntrenamiento> buscarSesion(Integer codigo);

    ClaseEntrenamiento almacenarSesion(ClaseEntrenamiento sesion);

    void quitarSesion(Integer codigo);
}
