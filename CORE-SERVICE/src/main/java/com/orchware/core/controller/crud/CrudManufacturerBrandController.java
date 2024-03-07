package com.orchware.core.controller.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.model.ManufacturerBrand;
import com.orchware.core.service.crud.ManufacturerBrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manufacturerBrand")
public class CrudManufacturerBrandController implements CrudController<ManufacturerBrand> {

    private final ManufacturerBrandService brandService;

    public CrudManufacturerBrandController(ManufacturerBrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public BaseResponse<?> save(ManufacturerBrand obj) {
        return brandService.saveResponse(obj);
    }

    @Override
    public BaseResponse<?> update(ManufacturerBrand obj) {
        return brandService.updateResponse(obj);
    }

    @Override
    public BaseResponse<?> getAll() {
        return brandService.getAllResponse();
    }

    @Override
    public BaseResponse<?> getById(Long id) {
        return brandService.getByIdResponse(id);
    }

    @Override
    public BaseResponse<?> delete(Long id) {
        return brandService.deleteByIdResponse(id);
    }

    @Override
    public BaseResponse<?> deleteAll() {
        return brandService.deleteAllResponse();
    }
}
