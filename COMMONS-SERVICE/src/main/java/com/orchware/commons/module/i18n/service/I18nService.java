package com.orchware.commons.module.i18n.service;

import com.orchware.commons.module.i18n.model.I18nMessage;

import java.util.List;

public interface I18nService {

    I18nMessage save(I18nMessage i18nMessage);

    I18nMessage update(I18nMessage i18nMessage, Long id);

    I18nMessage getById(Long id);

    List<I18nMessage> getByConstant(String constant);

    List<I18nMessage> getAll();

    String getMessage(Long id, String lang);

    String getMessage(String constant, String lang);

    void delete(Long id);

    void deleteAll();
}
