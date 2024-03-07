package com.orchware.core.repository;

import com.orchware.core.model.InterconnectSecKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InterconnectSecKeyRepository extends JpaRepository<InterconnectSecKey, Long> {

    Optional<InterconnectSecKey> findByUniqueKey(String uniqueKey);

    Optional<InterconnectSecKey> findByKeyhash(String keyhash);

    Optional<InterconnectSecKey> findByUniqueKeyAndKeyhashAndTimeStamp(String uniqueKey, String keyhash, long timeStamp);
}
