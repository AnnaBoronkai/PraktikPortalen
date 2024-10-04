package com.example.devops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/")
    public String home() {
        return "index";
    }




}


