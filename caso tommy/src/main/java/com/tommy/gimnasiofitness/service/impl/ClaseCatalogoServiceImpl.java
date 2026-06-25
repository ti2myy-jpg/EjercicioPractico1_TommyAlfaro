package com.tommy.gimnasiofitness.service.impl;

import com.tommy.gimnasiofitness.domain.ClaseEntrenamiento;
import com.tommy.gimnasiofitness.repository.ClaseEntrenamientoRepository;
import com.tommy.gimnasiofitness.repository.ReservacionClaseRepository;
import com.tommy.gimnasiofitness.service.ClaseCatalogoService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClaseCatalogoServiceImpl implements ClaseCatalogoService {

    private final ClaseEntrenamientoRepository archivoClases;
    private final ReservacionClaseRepository archivoReservas;

    public ClaseCatalogoServiceImpl(ClaseEntrenamientoRepository archivoClases,
            ReservacionClaseRepository archivoReservas) {
        this.archivoClases = archivoClases;
        this.archivoReservas = archivoReservas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseEntrenamiento> consultarSesiones() {
        return archivoClases.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClaseEntrenamiento> buscarSesion(Integer codigo) {
        return archivoClases.findById(codigo);
    }

    @Override
    @Transactional
    public ClaseEntrenamiento almacenarSesion(ClaseEntrenamiento sesion) {
        return archivoClases.save(sesion);
    }

    @Override
    @Transactional
    public void quitarSesion(Integer codigo) {
        if (!archivoClases.existsById(codigo)) {
            throw new IllegalArgumentException("La clase seleccionada no existe.");
        }
        if (!archivoReservas.findByServicioId(codigo).isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una clase con reservas.");
        }
        archivoClases.deleteById(codigo);
    }
}
