package com.example.barservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarRestController {

    private final Logger logger = LoggerFactory.getLogger(BarRestController.class);
    private BarService barService;

    public BarRestController(BarService barService) {
        this.barService = barService;
    }

    @GetMapping("/bar/{id}")
    public String bar(@PathVariable String id) {
        logger.info("Retrieving bar with {}", id);
        String response = barService.getBar(id);
        barService.deleteBar(id);
        return response;
    }
}
