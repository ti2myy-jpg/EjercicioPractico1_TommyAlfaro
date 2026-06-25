package com.tommy.gimnasiofitness.controllers;

import com.tommy.gimnasiofitness.domain.AreaEntrenamiento;
import com.tommy.gimnasiofitness.service.AreaCatalogoService;
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
@RequestMapping("/areas")
public class AreaController {

    private final AreaCatalogoService catalogoAreas;

    public AreaController(AreaCatalogoService catalogoAreas) {
        this.catalogoAreas = catalogoAreas;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("areasDisponibles", catalogoAreas.consultarAreas());
        model.addAttribute("areaNueva", new AreaEntrenamiento());
        return "areas/panel";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("areaNueva") AreaEntrenamiento areaNueva,
            BindingResult resultado, Model model, RedirectAttributes aviso) {
        if (resultado.hasErrors()) {
            model.addAttribute("areasDisponibles", catalogoAreas.consultarAreas());
            return "areas/panel";
        }
        catalogoAreas.almacenarArea(areaNueva);
        aviso.addFlashAttribute("mensajeBueno", "Categoria guardada.");
        return "redirect:/areas";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model, RedirectAttributes aviso) {
        return catalogoAreas.buscarArea(codigo)
                .map(area -> {
                    model.addAttribute("areaNueva", area);
                    return "areas/editar";
                })
                .orElseGet(() -> {
                    aviso.addFlashAttribute("mensajeMalo", "No se encontro la categoria solicitada.");
                    return "redirect:/areas";
                });
    }

    @PostMapping("/borrar")
    public String borrar(@RequestParam Integer codigo, RedirectAttributes aviso) {
        try {
            catalogoAreas.quitarArea(codigo);
            aviso.addFlashAttribute("mensajeBueno", "Categoria eliminada.");
        } catch (RuntimeException ex) {
            aviso.addFlashAttribute("mensajeMalo", ex.getMessage());
        }
        return "redirect:/areas";
    }
}
