package com.orchware.core.service.process.pool.registration;

import com.orchware.core.model.RegisteredDevice;
import com.orchware.core.service.process.pool.registration.dto.DeviceBrVarDefDTO;
import com.orchware.core.service.process.pool.registration.dto.DeviceRegistration;
import com.orchware.core.service.process.pool.registration.dto.RegisteredDeviceDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface DeviceRegistrationService {

    // Select device type
    List<String> getBrandCategories();

    // List brands definitions
    List<DeviceBrVarDefDTO> getBrandDefinitionsByBrandCategory(String brandCategory);

    // Select brand
    // Request for respective info for brand from DeviceBrVarDef

    // Submit info and create records RegisteredDevice + DeviceVarExt; Add a record in DeviceRegistrationPool
    RegisteredDeviceDTO registerDevice(DeviceRegistration deviceRegistration);
}
