package com.curso.jenkins.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFirstController {

    @GetMapping("/hola")
    public String controller(){
        return "Este es el primer servicio de la api";
    }
}
