package com.orchware.core.service.process.pool.registration;

import com.orchware.core.exceptions.NotFoundException;
import com.orchware.core.feignInterface.AccountInterface;
import com.orchware.core.feignInterface.CommonsInterface;
import com.orchware.core.model.*;
import com.orchware.core.repository.*;
import com.orchware.core.service.crud.DeviceBrVarDefService;
import com.orchware.core.service.process.agent.hash.KeyGenerationService;
import com.orchware.core.service.process.pool.registration.dto.DeviceBrVarDefDTO;
import com.orchware.core.service.process.pool.registration.dto.DeviceRegistration;
import com.orchware.core.service.process.pool.registration.dto.RegisteredDeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService {

    private static String SMART_KEY_SECRET = "SMART_KEY_SECRET";
    private static String AES = "AES";

    private final SystemAttributeRepository systemAttributeRepository;
    private final ManufacturerBrandRepository manufacturerBrandRepository;
    private final InterconnectSecKeyRepository interconnectSecKeyRepository;
    private final DeviceBrVarDefRepository deviceBrVarDefRepository;
    private final DeviceBrVarDefService deviceBrVarDefService;
    private final DeviceVarExtRepository deviceVarExtRepository;
    private final RegisteredDeviceRepository registeredDeviceRepository;
    private final DeviceRegistrationPoolRepository deviceRegistrationPoolRepository;
    private final KeyGenerationService keyGenerationService;
    private final CommonsInterface commonsInterface;
    private final AccountInterface accountInterface;
    private final ModelMapper modelMapper;

    DeviceRegistrationServiceImpl(SystemAttributeRepository systemAttributeRepository,
                                  ManufacturerBrandRepository manufacturerBrandRepository,
                                  InterconnectSecKeyRepository interconnectSecKeyRepository,
                                  DeviceBrVarDefRepository deviceBrVarDefRepository,
                                  DeviceBrVarDefService deviceBrVarDefService,
                                  RegisteredDeviceRepository registeredDeviceRepository,
                                  DeviceRegistrationPoolRepository deviceRegistrationPoolRepository,
                                  KeyGenerationService keyGenerationService,
                                  CommonsInterface commonsInterface,
                                  AccountInterface accountInterface,
                                  ModelMapper modelMapper,
                                  DeviceVarExtRepository deviceVarExtRepository) {
        this.systemAttributeRepository = systemAttributeRepository;
        this.manufacturerBrandRepository = manufacturerBrandRepository;
        this.interconnectSecKeyRepository = interconnectSecKeyRepository;
        this.deviceBrVarDefRepository = deviceBrVarDefRepository;
        this.deviceBrVarDefService = deviceBrVarDefService;
        this.deviceVarExtRepository = deviceVarExtRepository;
        this.registeredDeviceRepository = registeredDeviceRepository;
        this.deviceRegistrationPoolRepository = deviceRegistrationPoolRepository;
        this.keyGenerationService = keyGenerationService;
        this.commonsInterface = commonsInterface;
        this.accountInterface = accountInterface;
        this.modelMapper = modelMapper;
    }

    @Getter
    @AllArgsConstructor
    enum DeviceType {
        INVERTER("Inverter");
        private String name;

        public static List<String> getDeviceTypes() {
            return Arrays.stream(values()).map(v -> v.getName()).collect(Collectors.toList());
        }
    }

    @Override
    public List<String> getBrandCategories() {
        return DeviceType.getDeviceTypes();
    }

    @Override
    public List<DeviceBrVarDefDTO> getBrandDefinitionsByBrandCategory(String brandCategory) {
        List<ManufacturerBrand> manufacturerBrands = manufacturerBrandRepository.findManufacturerBrandByBrandCategory(brandCategory);
        return List.of(modelMapper.map(deviceBrVarDefRepository
                .findAllByManufacturerBrandInFetchAll(manufacturerBrands), DeviceBrVarDefDTO[].class));
    }

    @Override
    public RegisteredDeviceDTO registerDevice(DeviceRegistration deviceRegistration) {
        String smartKey = deviceRegistration.getSmartKey();
        RegisteredDeviceDTO registeredDeviceDTO = deviceRegistration.getDeviceData();
        String uniqueKey = smartKey.substring(0, 11);
        String keyhash = smartKey.substring(11, 16);
        long creationTimeStamp = Long.parseLong(smartKey.substring(16, 27));
        Optional<InterconnectSecKey> keyOptional = interconnectSecKeyRepository.findByUniqueKeyAndKeyhashAndTimeStamp(uniqueKey, keyhash, creationTimeStamp);
        InterconnectSecKey interconnectSecKey = keyOptional.orElseThrow(() -> new NotFoundException(InterconnectSecKey.class, "smartKey", smartKey));
        // add DeviceVarExt record
        DeviceVarExt deviceVarExt = modelMapper.map(registeredDeviceDTO.getDeviceVarExt(), DeviceVarExt.class);
        deviceVarExt.setAccountId(interconnectSecKey.getAccountId());
        DeviceBrVarDef deviceBrVarDef = deviceBrVarDefService.getById(registeredDeviceDTO.getDeviceVarExt().getDeviceBrVarDefId());
        deviceVarExt.setDeviceBrVarDef(deviceBrVarDef);
        deviceVarExt = deviceVarExtRepository.save(deviceVarExt);
        // add RegisteredDevice record
        RegisteredDevice registeredDevice = modelMapper.map(registeredDeviceDTO, RegisteredDevice.class);
        registeredDevice.setDeviceVarExt(deviceVarExt);
        registeredDeviceDTO = modelMapper.map(registeredDeviceRepository.save(registeredDevice), RegisteredDeviceDTO.class);
        // add to DeviceRegistrationPool record
        deviceRegistrationPoolRepository.save(DeviceRegistrationPool.builder()
                .accountId(interconnectSecKey.getAccountId())
                .brand(deviceBrVarDef.getManufacturerBrand())
                .registeredDevice(registeredDevice)
                .build());
        return registeredDeviceDTO;
    }
}
