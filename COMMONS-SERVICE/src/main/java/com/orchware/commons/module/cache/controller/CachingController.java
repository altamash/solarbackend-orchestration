package com.orchware.commons.module.cache.controller;

import com.orchware.commons.module.cache.service.CachingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController("CacheController")
//@Profile({"dev", "stage"})
@RequestMapping(value = "/cache")
public class CachingController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private CachingService cachingService;

    @GetMapping("/names")
    public Collection<String> getCacheNames() {
        return cachingService.getAllCacheNames();
    }

    @GetMapping("/name/{cacheName}/key/{keyCSV}")
    public void clearCache(@PathVariable("cacheName") String cacheName,
                           @PathVariable("keyCSV") String keyCSV) {
        cachingService.evictSingleCacheValue(cacheName, keyCSV);
        LOGGER.info("Cleared cache with key '{}' in cache", keyCSV, cacheName);
    }

    @GetMapping("/name/{cacheName}")
    public void clearCache(@PathVariable("cacheName") String cacheName) {
        cachingService.evictAllCacheValues(cacheName);
        LOGGER.info("Cleared cache {}", cacheName);
    }


    @GetMapping("/clearAllCaches")
    public void clearAllCaches() {
        cachingService.clearAllCaches();
    }
}
