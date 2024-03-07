package com.orchware.core.service.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.exceptions.NotFoundException;
import com.orchware.core.model.InterconnectSecKey;
import com.orchware.core.repository.InterconnectSecKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.orchware.core.constants.OrchConstants.Message.Service.CRUD.*;

@Service
public class InterconnectSecKeyService implements CrudService<InterconnectSecKey> {

    private final InterconnectSecKeyRepository repository;

    public InterconnectSecKeyService(InterconnectSecKeyRepository repository) {
        this.repository = repository;
    }

    @Override
    public InterconnectSecKey save(InterconnectSecKey obj) {
        return repository.save(obj);
    }

    @Override
    public InterconnectSecKey update(InterconnectSecKey obj) {
        return repository.save(obj);
    }

    @Override
    public List<InterconnectSecKey> getAll() {
        return repository.findAll();
    }

    @Override
    public InterconnectSecKey getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(InterconnectSecKey.class, id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(InterconnectSecKey.class, id);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public BaseResponse saveResponse(InterconnectSecKey obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(SAVE_SUCCESS, "InterconnectSecKey"))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse updateResponse(InterconnectSecKey obj) {
        return BaseResponse.builder()
                .data(repository.save(obj))
                .message(String.format(UPDATE_SUCCESS, "InterconnectSecKey", obj.getSecKeyId()))
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
                .data(repository.findById(id).orElseThrow(() -> new NotFoundException(InterconnectSecKey.class, id)))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteByIdResponse(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(InterconnectSecKey.class, id);
        }
        return BaseResponse.builder()
                .message(String.format(String.format(DELETE_SUCCESS, "InterconnectSecKey", id)))
                .code(200)
                .build();
    }

    @Override
    public BaseResponse deleteAllResponse() {
        repository.deleteAll();
        return BaseResponse.builder()
                .message(String.format(DELETE_ALL_SUCCESS, "InterconnectSecKey"))
                .code(200)
                .build();
    }
}
