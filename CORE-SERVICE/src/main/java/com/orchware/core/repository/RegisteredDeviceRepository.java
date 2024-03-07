package com.orchware.core.repository;

import com.orchware.core.model.RegisteredDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredDeviceRepository extends JpaRepository<RegisteredDevice, Long> {
}
