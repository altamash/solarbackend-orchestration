package com.orchware.core.controller.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.model.DeviceVarExt;
import com.orchware.core.service.crud.DeviceVarExtService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deviceVarExt")
public class CrudDeviceVarExtController implements CrudController<DeviceVarExt> {

    private final DeviceVarExtService deviceVarExtService;

    public CrudDeviceVarExtController(DeviceVarExtService deviceVarExtService) {
        this.deviceVarExtService = deviceVarExtService;
    }

    @Override
    public BaseResponse<?> save(DeviceVarExt obj) {
        return deviceVarExtService.saveResponse(obj);
    }

    @Override
    public BaseResponse<?> update(DeviceVarExt obj) {
        return deviceVarExtService.updateResponse(obj);
    }

    @Override
    public BaseResponse<?> getAll() {
        return deviceVarExtService.getAllResponse();
    }

    @Override
    public BaseResponse<?> getById(Long id) {
        return deviceVarExtService.getByIdResponse(id);
    }

    @Override
    public BaseResponse<?> delete(Long id) {
        return deviceVarExtService.deleteByIdResponse(id);
    }

    @Override
    public BaseResponse<?> deleteAll() {
        return deviceVarExtService.deleteAllResponse();
    }
}
