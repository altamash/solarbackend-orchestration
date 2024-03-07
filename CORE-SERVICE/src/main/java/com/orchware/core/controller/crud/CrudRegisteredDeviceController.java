package com.orchware.core.controller.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.model.RegisteredDevice;
import com.orchware.core.service.crud.RegisteredDeviceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registeredDevice")
public class CrudRegisteredDeviceController implements CrudController<RegisteredDevice> {

    private final RegisteredDeviceService registeredDeviceService;

    public CrudRegisteredDeviceController(RegisteredDeviceService registeredDeviceService) {
        this.registeredDeviceService = registeredDeviceService;
    }

    @Override
    public BaseResponse<?> save(RegisteredDevice obj) {
        return registeredDeviceService.saveResponse(obj);
    }

    @Override
    public BaseResponse<?> update(RegisteredDevice obj) {
        return registeredDeviceService.updateResponse(obj);
    }

    @Override
    public BaseResponse<?> getAll() {
        return registeredDeviceService.getAllResponse();
    }

    @Override
    public BaseResponse<?> getById(Long id) {
        return registeredDeviceService.getByIdResponse(id);
    }

    @Override
    public BaseResponse<?> delete(Long id) {
        return registeredDeviceService.deleteByIdResponse(id);
    }

    @Override
    public BaseResponse<?> deleteAll() {
        return registeredDeviceService.deleteAllResponse();
    }
}
