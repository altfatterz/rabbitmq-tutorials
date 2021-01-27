package com.example.barservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarRestController {

    private BarService barService;

    public BarRestController(BarService barService) {
        this.barService = barService;
    }

    @GetMapping("/bar/{id}")
    public String bar(@PathVariable String id) {
        return barService.bar(id);
    }
}
