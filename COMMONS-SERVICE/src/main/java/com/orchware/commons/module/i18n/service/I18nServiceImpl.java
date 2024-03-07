package com.orchware.commons.module.i18n.service;

import com.orchware.commons.module.i18n.repository.I18nRepository;
import com.orchware.commons.module.i18n.config.DBMessageSource;
import com.orchware.commons.module.i18n.model.I18nMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class I18nServiceImpl implements I18nService {

    private final I18nRepository i18nRepository;
    private final DBMessageSource messageSource;

    I18nServiceImpl(I18nRepository i18nRepository, DBMessageSource messageSource) {
        this.i18nRepository = i18nRepository;
        this.messageSource = messageSource;
    }

    @Override
    public I18nMessage save(I18nMessage i18nMessage) {
        return i18nRepository.save(i18nMessage);
    }

    @Override
    public I18nMessage update(I18nMessage i18nMessage, Long id) {
        I18nMessage i18nMessageDb = getById(id);
        if (i18nMessageDb != null) {
            i18nMessage = i18nRepository.save(i18nMessage);
        } else {
            throw new RuntimeException();
        }
        return i18nMessage;
    }

    @Override
    public I18nMessage getById(Long id) {
        return i18nRepository.findById(id).orElse(null);
    }

    @Override
    public List<I18nMessage> getByConstant(String constant) {
        return i18nRepository.findByConstant(constant);
    }

    @Override
    public List<I18nMessage> getAll() {
        return i18nRepository.findAll();
    }

    @Override
    public String getMessage(Long id, String lang) {
        I18nMessage m = getById(id);
        String message = null;
        if (m != null) {
            message = messageSource.getMessage(m.getConstant(), null, Locale.forLanguageTag(lang));
        }
        return message;
    }

    @Override
    public String getMessage(String constant, String lang) {
        List<I18nMessage> list = getByConstant(constant);
        String message = null;
        if (!list.isEmpty()) {
            message = messageSource.getMessage(list.get(0).getConstant(), null, Locale.forLanguageTag(lang));
        }
        return message;
    }

    @Override
    public void delete(Long id) {
        i18nRepository.delete(getById(id));
    }

    @Override
    public void deleteAll() {
        i18nRepository.deleteAll();
    }
}
