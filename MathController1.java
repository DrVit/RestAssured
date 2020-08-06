package com.flamexander.demo.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController       // Контроллер для 1 задания ДЗ
@RequestMapping("/api/v1/math1")
public class MathController1 {

    @GetMapping
    public String getMultiplication(@RequestParam Integer a, @RequestParam Integer b) {
        return String.valueOf(a * b);
    }

}
