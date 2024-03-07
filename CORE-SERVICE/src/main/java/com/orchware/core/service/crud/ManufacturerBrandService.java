package com.orchware.core.service.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.exceptions.NotFoundException;
import com.orchware.core.model.DeviceVarExt;
import com.orchware.core.model.ManufacturerBrand;
import com.orchware.core.repository.ManufacturerBrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.orchware.core.constants.OrchConstants.Message.Service.CRUD.*;

@Service
public class ManufacturerBrandService implements CrudService<ManufacturerBrand> {

    private final ManufacturerBrandRepository repository;

    public ManufacturerBrandService(ManufacturerBrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public ManufacturerBrand save(ManufacturerBrand obj) {
        return repository.save(obj);
    }

    @Override
    public ManufacturerBrand update(ManufacturerBrand obj) {
        return repository.save(obj);
    }

    @Override
    public List<ManufacturerBrand> getAll() {
        return repository.findAll();
    }

    @Override
    public ManufacturerBrand getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(ManufacturerBrand.class, id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(ManufacturerBrand.class, id);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public BaseResponse saveResponse(ManufacturerBrand obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(SAVE_SUCCESS, "DeviceVarDefMeta"))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse updateResponse(ManufacturerBrand obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(UPDATE_SUCCESS, "DeviceVarDefMeta", obj.getBrandId()))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse getAllResponse() {
        return BaseResponse.builder()
                .data(repository.findAll())
                .code(200)
                .build();
    }

    @Override
    public BaseResponse getByIdResponse(Long id) {
        return BaseResponse.builder()
                .data(repository.findById(id).orElseThrow(() -> new NotFoundException(ManufacturerBrand.class, id)))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteByIdResponse(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(ManufacturerBrand.class, id);
        }
        return BaseResponse.builder()
                .message(String.format(String.format(DELETE_SUCCESS, "DeviceVarDefMeta", id)))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteAllResponse() {
        repository.deleteAll();
        return BaseResponse.builder()
                .message(String.format(DELETE_ALL_SUCCESS, "DeviceVarDefMeta"))
                .code(200)
                .build();
    }
}
