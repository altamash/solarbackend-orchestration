package com.orchware.core.controller.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.model.DeviceBrVarDef;
import com.orchware.core.service.crud.DeviceBrVarDefService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deviceBrVarDef")
public class CrudDeviceBrVarDefController implements CrudController<DeviceBrVarDef> {

    private final DeviceBrVarDefService deviceBrVarDefService;

    public CrudDeviceBrVarDefController(DeviceBrVarDefService deviceBrVarDefService) {
        this.deviceBrVarDefService = deviceBrVarDefService;
    }

    @Override
    @Hidden
    public BaseResponse<?> save(DeviceBrVarDef obj) {
        return deviceBrVarDefService.saveResponse(obj);
    }

    @Override
    @Hidden
    public BaseResponse<?> update(DeviceBrVarDef obj) {
        return deviceBrVarDefService.updateResponse(obj);
    }

    @GetMapping("/test")
    public String test() {
        return "Working";
    }

    @PostMapping("/brandId/{manufacturerBrandId}")
    public BaseResponse<?> save(@RequestBody DeviceBrVarDef obj, @PathVariable Long manufacturerBrandId) {
        return deviceBrVarDefService.save(obj, manufacturerBrandId);
    }

    @PutMapping("/defId/{id}/brandId/{manufacturerBrandId}")
    public BaseResponse<?> update(@RequestBody DeviceBrVarDef obj, Long id, @PathVariable Long manufacturerBrandId) {
        return deviceBrVarDefService.update(obj, id, manufacturerBrandId);
    }

    @Override
    public BaseResponse<?> getAll() {
        return deviceBrVarDefService.getAllResponse();
    }

    @Override
    public BaseResponse<?> getById(Long id) {
        return deviceBrVarDefService.getByIdResponse(id);
    }

    @Override
    public BaseResponse<?> delete(Long id) {
        return deviceBrVarDefService.deleteByIdResponse(id);
    }

    @Override
    public BaseResponse<?> deleteAll() {
        return deviceBrVarDefService.deleteAllResponse();
    }
}
