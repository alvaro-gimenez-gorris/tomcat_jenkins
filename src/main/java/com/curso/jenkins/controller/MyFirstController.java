package com.curso.jenkins.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFirstController {

    @GetMapping("/hola")
    public String controller(){
        return "Hola desde el servidor de desarrollo";
    }
}
