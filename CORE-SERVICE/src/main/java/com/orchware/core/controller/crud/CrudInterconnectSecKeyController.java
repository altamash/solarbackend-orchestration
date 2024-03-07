package com.orchware.core.controller.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.model.InterconnectSecKey;
import com.orchware.core.service.crud.InterconnectSecKeyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interconnectSecKey")
public class CrudInterconnectSecKeyController implements CrudController<InterconnectSecKey> {

    private final InterconnectSecKeyService interconnectSecKeyService;

    public CrudInterconnectSecKeyController(InterconnectSecKeyService interconnectSecKeyService) {
        this.interconnectSecKeyService = interconnectSecKeyService;
    }

    @Override
    public BaseResponse<?> save(InterconnectSecKey obj) {
        return interconnectSecKeyService.saveResponse(obj);
    }

    @Override
    public BaseResponse<?> update(InterconnectSecKey obj) {
        return interconnectSecKeyService.updateResponse(obj);
    }

    @Override
    public BaseResponse<?> getAll() {
        return interconnectSecKeyService.getAllResponse();
    }

    @Override
    public BaseResponse<?> getById(Long id) {
        return interconnectSecKeyService.getByIdResponse(id);
    }

    @Override
    public BaseResponse<?> delete(Long id) {
        return interconnectSecKeyService.deleteByIdResponse(id);
    }

    @Override
    public BaseResponse<?> deleteAll() {
        return interconnectSecKeyService.deleteAllResponse();
    }
}
