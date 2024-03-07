package com.orchware.core.repository;

import com.orchware.core.model.DeviceBrVarDef;
import com.orchware.core.model.ManufacturerBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceBrVarDefRepository extends JpaRepository<DeviceBrVarDef, Long> {
    @Query("select dbv from DeviceBrVarDef dbv LEFT JOIN FETCH dbv.manufacturerBrand mb LEFT JOIN FETCH dbv.deviceVarDefMeta dvm")
    List<DeviceBrVarDef> findAllFetchAll();
    @Query("select dbv from DeviceBrVarDef dbv LEFT JOIN FETCH dbv.manufacturerBrand mb LEFT JOIN FETCH dbv.deviceVarDefMeta dvm " +
            "where dbv.manufacturerBrand in (:manufacturerBrand)")
    List<DeviceBrVarDef> findAllByManufacturerBrandInFetchAll(List<ManufacturerBrand> manufacturerBrand);
}
