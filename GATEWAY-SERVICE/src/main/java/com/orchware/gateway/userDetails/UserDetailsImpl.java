package com.orchware.gateway.userDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orchware.gateway.responseDTO.AccountUserDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private AccountUserDTO user;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password, AccountUserDTO user,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.user = user;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(AccountUserDTO accountUserDTO) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (accountUserDTO.getAccountUserType() != null) {
            authorities.addAll(accountUserDTO.getAccountUserType().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));
        }
        return new UserDetailsImpl(
                accountUserDTO.getUserId(),
                accountUserDTO.getUsername(),
                accountUserDTO.getEmail(),
                accountUserDTO.getPassword(),
                accountUserDTO,
                authorities);
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

//    @Override
//    public boolean isEnabled() {
//        return user.getStatus() != null && user.getStatus().equals(EUserStatus.ACTIVE.getStatus());
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
