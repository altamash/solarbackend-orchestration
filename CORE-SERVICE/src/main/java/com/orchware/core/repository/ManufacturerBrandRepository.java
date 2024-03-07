package com.orchware.core.repository;

import com.orchware.core.model.ManufacturerBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManufacturerBrandRepository extends JpaRepository<ManufacturerBrand, Long> {
    @Query("select mb from ManufacturerBrand mb")
    List<ManufacturerBrand> findManufacturerBrandByBrandCategory(String brandCategory);
}
