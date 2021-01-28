package com.example.barservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BarRestController {

    private final Logger logger = LoggerFactory.getLogger(BarRestController.class);
    private RetryableBarService retryableBarService;
    private BarService barService;

    public BarRestController(RetryableBarService retryableBarService, BarService barService) {
        this.retryableBarService = retryableBarService;
        this.barService = barService;
    }

    @GetMapping("/bar/{id}")
    public ResponseEntity<?> bar(@PathVariable String id) {
        logger.info("Retrieving bar with {}", id);

        String response = retryableBarService.getBar(id);

        // invalidate the cache right away.
        barService.deleteBar(id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.of(Optional.empty());
    }

    @GetMapping("/cache-details")
    public ResponseEntity<Void> details() {

        barService.cacheDetails();

        return ResponseEntity.ok().build();
    }

}
