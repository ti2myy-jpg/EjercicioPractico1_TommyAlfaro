package com.tommy.gimnasiofitness.service;

import com.tommy.gimnasiofitness.domain.ReservacionClase;
import java.util.List;
import java.util.Optional;

public interface ReservacionCatalogoService {

    List<ReservacionClase> consultarCupos();

    Optional<ReservacionClase> buscarCupo(Integer codigo);

    ReservacionClase almacenarCupo(ReservacionClase cupo);

    void quitarCupo(Integer codigo);
}
