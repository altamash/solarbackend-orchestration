package com.orchware.account.model.accountType;

import java.util.Arrays;

public enum EAccountUserType {

    BASIC("BASIC"),
    STANDARD("STANDARD"),
    COMMERCIAL("COMMERCIAL"),
    PREMIUM("PREMIUM");
    String name;

    EAccountUserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EAccountUserType get(String name) {
        return Arrays.stream(values()).filter(value -> name.equalsIgnoreCase(value.name)).findFirst().orElse(null);
    }
}
