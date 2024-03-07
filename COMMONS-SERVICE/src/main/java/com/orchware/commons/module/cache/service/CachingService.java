package com.orchware.commons.module.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CachingService {

    @Autowired
    private CacheManager cacheManager;

    public Collection<String> getAllCacheNames() {
        return cacheManager.getCacheNames();
    }

    public void evictSingleCacheValue(String cacheName, String keyCSV) {
        List<Object> key = Arrays.stream(keyCSV.split(","))
                .map(k -> {
                        k = k.trim();
                        try {
                            return Long.parseLong(k);
                        } catch(Exception e) {
                            return k;
                        }
                })
                .collect(Collectors.toList());
//        key.set(0, Long.parseLong((String) key.get(0)));
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }

    public void evictAllCacheValues(String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    public void clearAllCaches() {
        cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    private Cache getCache(String cacheName) {
        return cacheManager.getCache(cacheName);
    }
}