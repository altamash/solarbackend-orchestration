package com.orchware.core.service.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.exceptions.NotFoundException;
import com.orchware.core.model.DeviceBrVarDef;
import com.orchware.core.model.ManufacturerBrand;
import com.orchware.core.repository.DeviceBrVarDefRepository;
import com.orchware.core.repository.ManufacturerBrandRepository;
import com.orchware.core.service.process.pool.registration.dto.DeviceBrVarDefDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.orchware.core.constants.OrchConstants.Message.Service.CRUD.*;

@Service
public class DeviceBrVarDefService implements CrudService<DeviceBrVarDef>, DeviceBrVarDefServiceExt {

    private final DeviceBrVarDefRepository repository;
    private final ManufacturerBrandRepository manufacturerBrandRepository;
    private final ModelMapper modelMapper;

    public DeviceBrVarDefService(DeviceBrVarDefRepository repository,
                                 ManufacturerBrandRepository manufacturerBrandRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.manufacturerBrandRepository = manufacturerBrandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DeviceBrVarDef save(DeviceBrVarDef obj) {
        return repository.save(obj);
    }

    @Override
    public DeviceBrVarDef update(DeviceBrVarDef obj) {
        return repository.save(obj);
    }

    @Override
    public List<DeviceBrVarDef> getAll() {
        return repository.findAllFetchAll();
    }

    @Override
    public DeviceBrVarDef getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(DeviceBrVarDef.class, id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(DeviceBrVarDef.class, id);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public BaseResponse saveResponse(DeviceBrVarDef obj) {
        return BaseResponse.builder()
                .data(save(obj))
                .message(String.format(SAVE_SUCCESS, "DeviceBrVarDef"))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse updateResponse(DeviceBrVarDef obj) {
        return BaseResponse.builder()
                .data(update(obj))
                .message(String.format(UPDATE_SUCCESS, "DeviceBrVarDef", obj.getVarDefId()))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse getAllResponse() {
        return BaseResponse.builder()
                .data(modelMapper.map(getAll(), DeviceBrVarDefDTO[].class))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse getByIdResponse(Long id) {
        return BaseResponse.builder()
                .data(getById(id))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteByIdResponse(Long id) {
        deleteById(id);
        return BaseResponse.builder()
                .message(String.format(DELETE_SUCCESS, "DeviceBrVarDef", id))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteAllResponse() {
        deleteAll();
        return BaseResponse.builder()
                .message(String.format(DELETE_ALL_SUCCESS, "DeviceBrVarDefs"))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse save(DeviceBrVarDef obj, Long manufacturerBrandId) {
        obj.setManufacturerBrand(manufacturerBrandRepository.findById(manufacturerBrandId).orElseThrow(() ->
                new NotFoundException(ManufacturerBrand.class, manufacturerBrandId)));
        return BaseResponse.builder()
                .data(new ModelMapper().map(repository.save(obj), DeviceBrVarDef.class))
                .message(String.format(SAVE_SUCCESS, "DeviceBrVarDef"))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse update(DeviceBrVarDef obj, Long id, Long manufacturerBrandId) {
        DeviceBrVarDef deviceBrVarDefDb = repository.findById(id).orElseThrow(() -> new NotFoundException(DeviceBrVarDef.class, id));
        new ModelMapper().map(obj, deviceBrVarDefDb);
        deviceBrVarDefDb.setManufacturerBrand(manufacturerBrandRepository.findById(manufacturerBrandId).orElseThrow(() ->
                new NotFoundException(ManufacturerBrand.class, manufacturerBrandId)));
        return BaseResponse.builder()
                .data(new ModelMapper().map(repository.save(deviceBrVarDefDb), DeviceBrVarDefDTO.class))
                .message(String.format(UPDATE_SUCCESS, "DeviceBrVarDef", obj.getVarDefId()))
                .code(200)
                .build();
    }
}
