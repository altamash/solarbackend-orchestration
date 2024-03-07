package com.orchware.core.service.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.exceptions.NotFoundException;
import com.orchware.core.model.DeviceVarDefMeta;
import com.orchware.core.model.DeviceVarExt;
import com.orchware.core.repository.DeviceVarExtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.orchware.core.constants.OrchConstants.Message.Service.CRUD.*;

@Service
public class DeviceVarExtService implements CrudService<DeviceVarExt> {

    private final DeviceVarExtRepository repository;

    public DeviceVarExtService(DeviceVarExtRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeviceVarExt save(DeviceVarExt obj) {
        return repository.save(obj);
    }

    @Override
    public DeviceVarExt update(DeviceVarExt obj) {
        return repository.save(obj);
    }

    @Override
    public List<DeviceVarExt> getAll() {
        return repository.findAll();
    }

    @Override
    public DeviceVarExt getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(DeviceVarExt.class, id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(DeviceVarExt.class, id);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public BaseResponse saveResponse(DeviceVarExt obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(SAVE_SUCCESS, "DeviceVarExt"))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse updateResponse(DeviceVarExt obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(UPDATE_SUCCESS, "DeviceVarExt", obj.getDeviceId()))
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
                .data(repository.findById(id).orElseThrow(() -> new NotFoundException(DeviceVarExt.class, id)))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteByIdResponse(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(DeviceVarExt.class, id);
        }
        return BaseResponse.builder()
                .message(String.format(String.format(DELETE_SUCCESS, "DeviceVarExt", id)))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteAllResponse() {
        repository.deleteAll();
        return BaseResponse.builder()
                .message(String.format(DELETE_ALL_SUCCESS, "DeviceVarExt"))
                .code(200)
                .build();
    }
}
