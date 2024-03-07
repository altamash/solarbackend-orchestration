package com.orchware.login.requestDTO;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 6/25/19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO implements Serializable {
    private String username;
    private String emailAddress;
}
