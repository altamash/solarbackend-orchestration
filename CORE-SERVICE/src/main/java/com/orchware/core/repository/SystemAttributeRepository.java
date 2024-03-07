package com.orchware.core.repository;

import com.orchware.core.model.SystemAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemAttributeRepository extends JpaRepository<SystemAttribute, Long> {
    Optional<SystemAttribute> findByAttributeKey(String attributeKey);
}
