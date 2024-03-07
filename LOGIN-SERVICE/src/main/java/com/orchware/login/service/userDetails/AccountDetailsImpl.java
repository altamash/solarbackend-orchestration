package com.orchware.login.service.userDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orchware.login.responseDTO.account.UserAuthDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    public AccountDetailsImpl() {
    }

    public AccountDetailsImpl(Long id, String username, String email, UserAuthDTO userAuthDTO, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userAuthDTO = userAuthDTO;
        this.password = password;
        this.authorities = authorities;
    }

    private Long id;
    private String username;
    private String email;
    private UserAuthDTO userAuthDTO;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static AccountDetailsImpl build(UserAuthDTO userAuthDTO) {
        return new AccountDetailsImpl(
                userAuthDTO.getUserAuthId(),
                userAuthDTO.getUsername(),
                userAuthDTO.getAuthLinkEmail(),
                userAuthDTO,
                userAuthDTO.getPassword(),
                Collections.EMPTY_LIST);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AccountDetailsImpl accountDetails = (AccountDetailsImpl) o;
        return Objects.equals(id, accountDetails.id);
    }
}
