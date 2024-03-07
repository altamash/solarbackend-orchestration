package com.orchware.core.controller.crud;

import com.orchware.core.controller.BaseResponse;
import org.springframework.web.bind.annotation.*;

public interface CrudController<U> {

    @PostMapping()
    BaseResponse<?> save(@RequestBody U obj);

    @PutMapping()
    BaseResponse<?> update(@RequestBody U obj);

    @GetMapping()
    BaseResponse<?> getAll();

    @GetMapping("/{id}")
    BaseResponse<?> getById(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    BaseResponse<?> delete(@PathVariable("id") Long id);

    @DeleteMapping
    BaseResponse<?> deleteAll();
}
