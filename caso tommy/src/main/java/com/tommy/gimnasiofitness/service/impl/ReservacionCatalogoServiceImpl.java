package com.tommy.gimnasiofitness.service.impl;

import com.tommy.gimnasiofitness.domain.ReservacionClase;
import com.tommy.gimnasiofitness.repository.ReservacionClaseRepository;
import com.tommy.gimnasiofitness.service.ReservacionCatalogoService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservacionCatalogoServiceImpl implements ReservacionCatalogoService {

    private final ReservacionClaseRepository archivoReservas;

    public ReservacionCatalogoServiceImpl(ReservacionClaseRepository archivoReservas) {
        this.archivoReservas = archivoReservas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservacionClase> consultarCupos() {
        return archivoReservas.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReservacionClase> buscarCupo(Integer codigo) {
        return archivoReservas.findById(codigo);
    }

    @Override
    @Transactional
    public ReservacionClase almacenarCupo(ReservacionClase cupo) {
        return archivoReservas.save(cupo);
    }

    @Override
    @Transactional
    public void quitarCupo(Integer codigo) {
        if (!archivoReservas.existsById(codigo)) {
            throw new IllegalArgumentException("La reserva seleccionada no existe.");
        }
        archivoReservas.deleteById(codigo);
    }
}
