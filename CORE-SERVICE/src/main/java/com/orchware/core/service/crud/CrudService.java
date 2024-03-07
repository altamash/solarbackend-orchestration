package com.orchware.core.service.crud;

import com.orchware.core.controller.BaseResponse;

import java.util.List;

public interface CrudService<T> {
    T save(T obj);

    T update(T obj);

    List<T> getAll();

    T getById(Long id);

    void deleteById(Long id);

    void deleteAll();

    BaseResponse saveResponse(T obj);

    BaseResponse updateResponse(T obj);

    BaseResponse getAllResponse();

    BaseResponse getByIdResponse(Long id);

    BaseResponse deleteByIdResponse(Long id);

    BaseResponse deleteAllResponse();
}
