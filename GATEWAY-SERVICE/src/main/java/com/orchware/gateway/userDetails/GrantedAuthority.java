package com.orchware.gateway.userDetails;

import java.io.Serializable;

public interface GrantedAuthority extends Serializable {
    String getAuthority();
}
