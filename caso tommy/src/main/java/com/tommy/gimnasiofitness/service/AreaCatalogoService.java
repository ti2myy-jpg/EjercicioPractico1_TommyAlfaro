package com.tommy.gimnasiofitness.service;

import com.tommy.gimnasiofitness.domain.AreaEntrenamiento;
import java.util.List;
import java.util.Optional;

public interface AreaCatalogoService {

    List<AreaEntrenamiento> consultarAreas();

    Optional<AreaEntrenamiento> buscarArea(Integer codigo);

    AreaEntrenamiento almacenarArea(AreaEntrenamiento area);

    void quitarArea(Integer codigo);
}
