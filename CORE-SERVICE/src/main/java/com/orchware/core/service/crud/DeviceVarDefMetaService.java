package com.orchware.core.service.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.exceptions.NotFoundException;
import com.orchware.core.model.DeviceBrVarDef;
import com.orchware.core.model.DeviceVarDefMeta;
import com.orchware.core.repository.DeviceVarDefMetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.orchware.core.constants.OrchConstants.Message.Service.CRUD.*;

@Service
public class DeviceVarDefMetaService implements CrudService<DeviceVarDefMeta> {

    private final DeviceVarDefMetaRepository repository;

    public DeviceVarDefMetaService(DeviceVarDefMetaRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeviceVarDefMeta save(DeviceVarDefMeta obj) {
        return repository.save(obj);
    }

    @Override
    public DeviceVarDefMeta update(DeviceVarDefMeta obj) {
        return repository.save(obj);
    }

    @Override
    public List<DeviceVarDefMeta> getAll() {
        return repository.findAll();
    }

    @Override
    public DeviceVarDefMeta getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(DeviceVarDefMeta.class, id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(DeviceVarDefMeta.class, id);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public BaseResponse saveResponse(DeviceVarDefMeta obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(SAVE_SUCCESS, "DeviceVarDefMeta"))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse updateResponse(DeviceVarDefMeta obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(UPDATE_SUCCESS, "DeviceVarDefMeta", obj.getVarDefId()))
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
                .data(repository.findById(id).orElseThrow(() -> new NotFoundException(DeviceVarDefMeta.class, id)))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteByIdResponse(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(DeviceVarDefMeta.class, id);
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
