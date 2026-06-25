package com.tommy.gimnasiofitness.service.impl;

import com.tommy.gimnasiofitness.domain.AreaEntrenamiento;
import com.tommy.gimnasiofitness.repository.AreaEntrenamientoRepository;
import com.tommy.gimnasiofitness.repository.ClaseEntrenamientoRepository;
import com.tommy.gimnasiofitness.service.AreaCatalogoService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaCatalogoServiceImpl implements AreaCatalogoService {

    private final AreaEntrenamientoRepository archivoAreas;
    private final ClaseEntrenamientoRepository archivoClases;

    public AreaCatalogoServiceImpl(AreaEntrenamientoRepository archivoAreas,
            ClaseEntrenamientoRepository archivoClases) {
        this.archivoAreas = archivoAreas;
        this.archivoClases = archivoClases;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaEntrenamiento> consultarAreas() {
        return archivoAreas.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AreaEntrenamiento> buscarArea(Integer codigo) {
        return archivoAreas.findById(codigo);
    }

    @Override
    @Transactional
    public AreaEntrenamiento almacenarArea(AreaEntrenamiento area) {
        return archivoAreas.save(area);
    }

    @Override
    @Transactional
    public void quitarArea(Integer codigo) {
        if (!archivoAreas.existsById(codigo)) {
            throw new IllegalArgumentException("La categoria seleccionada no existe.");
        }
        if (!archivoClases.findByCategoriaId(codigo).isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una categoria que tiene clases asociadas.");
        }
        archivoAreas.deleteById(codigo);
    }
}
