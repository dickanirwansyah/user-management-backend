package com.app.backend.backend_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/ping")
public class PingController {

    @GetMapping
    public String pong(){
        return "PING !!";
    }
}
