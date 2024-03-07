package com.orchware.core.controller.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.model.DeviceVarDefMeta;
import com.orchware.core.service.crud.DeviceVarDefMetaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/deviceVarDefMeta")
public class CrudDeviceVarDefMetaController implements CrudController<DeviceVarDefMeta> {

    private final DeviceVarDefMetaService deviceVarDefMetaService;

    public CrudDeviceVarDefMetaController(DeviceVarDefMetaService deviceVarDefMetaService) {
        this.deviceVarDefMetaService = deviceVarDefMetaService;
    }

    @Override
    public BaseResponse<?> save(DeviceVarDefMeta obj) {
        return deviceVarDefMetaService.saveResponse(obj);
    }

    @Override
    public BaseResponse<?> update(DeviceVarDefMeta obj) {
        return deviceVarDefMetaService.updateResponse(obj);
    }

    @Override
    public BaseResponse<?> getAll() {
        return deviceVarDefMetaService.getAllResponse();
    }

    @Override
    public BaseResponse<?> getById(Long id) {
        return deviceVarDefMetaService.getByIdResponse(id);
    }

    @Override
    public BaseResponse<?> delete(Long id) {
        return deviceVarDefMetaService.deleteByIdResponse(id);
    }

    @Override
    public BaseResponse<?> deleteAll() {
        return deviceVarDefMetaService.deleteAllResponse();
    }
}
