package com.orchware.core.service.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.exceptions.NotFoundException;
import com.orchware.core.model.DeviceVarExt;
import com.orchware.core.model.ManufacturerBrand;
import com.orchware.core.model.RegisteredDevice;
import com.orchware.core.repository.RegisteredDeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.orchware.core.constants.OrchConstants.Message.Service.CRUD.*;

@Service
public class RegisteredDeviceService implements CrudService<RegisteredDevice> {

    private final RegisteredDeviceRepository repository;

    public RegisteredDeviceService(RegisteredDeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public RegisteredDevice save(RegisteredDevice obj) {
        return repository.save(obj);
    }

    @Override
    public RegisteredDevice update(RegisteredDevice obj) {
        return repository.save(obj);
    }

    @Override
    public List<RegisteredDevice> getAll() {
        return repository.findAll();
    }

    @Override
    public RegisteredDevice getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(RegisteredDevice.class, id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(RegisteredDevice.class, id);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public BaseResponse saveResponse(RegisteredDevice obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(SAVE_SUCCESS, "DeviceVarDefMeta"))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse updateResponse(RegisteredDevice obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(UPDATE_SUCCESS, "DeviceVarDefMeta", obj.getDeviceId()))
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
