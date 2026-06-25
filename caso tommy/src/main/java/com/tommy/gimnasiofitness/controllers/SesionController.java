package com.tommy.gimnasiofitness.controllers;

import com.tommy.gimnasiofitness.domain.ClaseEntrenamiento;
import com.tommy.gimnasiofitness.service.AreaCatalogoService;
import com.tommy.gimnasiofitness.service.ClaseCatalogoService;
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
@RequestMapping("/sesiones")
public class SesionController {

    private final ClaseCatalogoService catalogoClases;
    private final AreaCatalogoService catalogoAreas;

    public SesionController(ClaseCatalogoService catalogoClases, AreaCatalogoService catalogoAreas) {
        this.catalogoClases = catalogoClases;
        this.catalogoAreas = catalogoAreas;
    }

    @GetMapping
    public String listar(Model model) {
        cargarDatos(model, new ClaseEntrenamiento());
        return "sesiones/panel";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("sesionNueva") ClaseEntrenamiento sesionNueva,
            BindingResult resultado, Model model, RedirectAttributes aviso) {
        if (resultado.hasErrors()) {
            cargarDatos(model, sesionNueva);
            return "sesiones/panel";
        }
        catalogoClases.almacenarSesion(sesionNueva);
        aviso.addFlashAttribute("mensajeBueno", "Clase guardada.");
        return "redirect:/sesiones";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model, RedirectAttributes aviso) {
        return catalogoClases.buscarSesion(codigo)
                .map(clase -> {
                    model.addAttribute("sesionNueva", clase);
                    model.addAttribute("areasElegibles", catalogoAreas.consultarAreas());
                    return "sesiones/editar";
                })
                .orElseGet(() -> {
                    aviso.addFlashAttribute("mensajeMalo", "No se encontro la clase solicitada.");
                    return "redirect:/sesiones";
                });
    }

    @PostMapping("/borrar")
    public String borrar(@RequestParam Integer codigo, RedirectAttributes aviso) {
        try {
            catalogoClases.quitarSesion(codigo);
            aviso.addFlashAttribute("mensajeBueno", "Clase eliminada.");
        } catch (RuntimeException ex) {
            aviso.addFlashAttribute("mensajeMalo", ex.getMessage());
        }
        return "redirect:/sesiones";
    }

    private void cargarDatos(Model model, ClaseEntrenamiento clase) {
        model.addAttribute("sesionNueva", clase);
        model.addAttribute("sesionesDisponibles", catalogoClases.consultarSesiones());
        model.addAttribute("areasElegibles", catalogoAreas.consultarAreas());
    }
}
