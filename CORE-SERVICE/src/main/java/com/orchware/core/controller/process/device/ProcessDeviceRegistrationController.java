package com.orchware.core.controller.process.device;

import com.orchware.core.service.process.pool.registration.DeviceRegistrationService;
import com.orchware.core.service.process.pool.registration.dto.DeviceBrVarDefDTO;
import com.orchware.core.service.process.pool.registration.dto.DeviceRegistration;
import com.orchware.core.service.process.pool.registration.dto.RegisteredDeviceDTO;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/device")
public class ProcessDeviceRegistrationController {

    private final DeviceRegistrationService deviceRegistrationService;

    public ProcessDeviceRegistrationController(DeviceRegistrationService deviceRegistrationService) {
        this.deviceRegistrationService = deviceRegistrationService;
    }

//    @GetMapping("/validateSmartKey")
//    public Object validateSmartKey(@RequestHeader String smartKey) {
//        return deviceRegistrationService.validateSmartKey(smartKey);
//    }

    @GetMapping("/deviceTypes")
    public List<String> getDeviceTypes() {
        return deviceRegistrationService.getBrandCategories();
    }

    @GetMapping("/brandDefinitions/{brandCategory}")
    public List<DeviceBrVarDefDTO> getManufacturerBrandByCategory(@PathVariable String brandCategory) {
        return deviceRegistrationService.getBrandDefinitionsByBrandCategory(brandCategory);
    }

    @PostMapping("/registerDevice")
    public RegisteredDeviceDTO registerDevice(@RequestBody DeviceRegistration deviceRegistration) {
        return deviceRegistrationService.registerDevice(deviceRegistration);
    }
}
