package com.orchware.commons.module.cache;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cache {

    private String name;
    private String keyClass;
    private String valueClass;
    private long heap;
    private long offHeap;
    private long ttlOrtti;
    private String expiryPolicy;
}
