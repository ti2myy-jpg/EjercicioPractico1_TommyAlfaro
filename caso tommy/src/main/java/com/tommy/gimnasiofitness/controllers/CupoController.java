package com.tommy.gimnasiofitness.controllers;

import com.tommy.gimnasiofitness.domain.ReservacionClase;
import com.tommy.gimnasiofitness.service.ClaseCatalogoService;
import com.tommy.gimnasiofitness.service.ReservacionCatalogoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cupos")
public class CupoController {

    private final ReservacionCatalogoService catalogoReservas;
    private final ClaseCatalogoService catalogoClases;

    public CupoController(ReservacionCatalogoService catalogoReservas, ClaseCatalogoService catalogoClases) {
        this.catalogoReservas = catalogoReservas;
        this.catalogoClases = catalogoClases;
    }

    @GetMapping
    public String listar(Model model) {
        preparar(model, new ReservacionClase());
        return "cupos/panel";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cupoNuevo") ReservacionClase cupoNuevo,
            BindingResult resultado, Model model, RedirectAttributes aviso) {
        if (resultado.hasErrors()) {
            preparar(model, cupoNuevo);
            return "cupos/panel";
        }
        catalogoReservas.almacenarCupo(cupoNuevo);
        aviso.addFlashAttribute("mensajeBueno", "Reserva registrada.");
        return "redirect:/cupos";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model, RedirectAttributes aviso) {
        return catalogoReservas.buscarCupo(codigo)
                .map(cupo -> {
                    model.addAttribute("cupoNuevo", cupo);
                    model.addAttribute("sesionesElegibles", catalogoClases.consultarSesiones());
                    return "cupos/editar";
                })
                .orElseGet(() -> {
                    aviso.addFlashAttribute("mensajeMalo", "No se encontro la reserva solicitada.");
                    return "redirect:/cupos";
                });
    }

    @PostMapping("/borrar")
    public String borrar(@RequestParam Integer codigo, RedirectAttributes aviso) {
        try {
            catalogoReservas.quitarCupo(codigo);
            aviso.addFlashAttribute("mensajeBueno", "Reserva eliminada.");
        } catch (RuntimeException ex) {
            aviso.addFlashAttribute("mensajeMalo", ex.getMessage());
        }
        return "redirect:/cupos";
    }

    private void preparar(Model model, ReservacionClase cupo) {
        model.addAttribute("cupoNuevo", cupo);
        model.addAttribute("reservasAgenda", catalogoReservas.consultarCupos());
        model.addAttribute("sesionesElegibles", catalogoClases.consultarSesiones());
    }
}
