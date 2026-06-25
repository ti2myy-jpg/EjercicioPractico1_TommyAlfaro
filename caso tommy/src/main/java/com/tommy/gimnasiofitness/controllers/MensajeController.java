package com.tommy.gimnasiofitness.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mensajes")
public class MensajeController {

    @GetMapping
    public String verFormulario() {
        return "mensajes/formulario";
    }

    @PostMapping("/enviar")
    public String recibir(RedirectAttributes aviso) {
        aviso.addFlashAttribute("mensajeBueno", "Mensaje recibido por Gimnasio Fitness.");
        return "redirect:/mensajes";
    }
}
