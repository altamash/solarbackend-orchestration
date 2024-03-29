package com.orchware.account.repository;

import com.orchware.account.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository  extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findUserAuthByUsername(String username);
}
