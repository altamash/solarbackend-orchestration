package com.orchware.commons.module.streams.service;

import java.util.Arrays;

public enum EAuthMethod {
    MANAGED_IDENTITY("Authenticate via Managed Identity", 1),
    AAD_TOKEN_USING_AuthCallback("Get AAD token using AuthCallback", 2),
    AAD_TOKEN_USING_AzureActiveDirectoryTokenProvider("Get AAD token using AzureActiveDirectoryTokenProvider to wrap AuthCallback", 3),
    AAD_TOKEN_USING_ITokenProvider("Get AAD token using ITokenProvider implementation", 4);
    private String name;
    private int number;

    EAuthMethod(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public static EAuthMethod get(int number) {
        return Arrays.stream(values()).filter(value -> number == value.number).findFirst().orElse(null);
    }

}
