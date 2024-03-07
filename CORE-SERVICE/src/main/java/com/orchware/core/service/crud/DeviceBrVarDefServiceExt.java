package com.orchware.core.service.crud;

import com.orchware.core.controller.BaseResponse;
import com.orchware.core.model.DeviceBrVarDef;

public interface DeviceBrVarDefServiceExt {
    BaseResponse save(DeviceBrVarDef obj, Long manufacturerBrandId);

    BaseResponse update(DeviceBrVarDef obj, Long id, Long manufacturerBrandId);
}
