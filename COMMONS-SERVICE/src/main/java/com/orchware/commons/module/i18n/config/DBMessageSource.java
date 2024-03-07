package com.orchware.commons.module.i18n.config;

import com.orchware.commons.module.i18n.model.I18nMessage;
import com.orchware.commons.module.i18n.repository.I18nRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component("messageSource")
public class DBMessageSource implements MessageSource {

    @Autowired
    private I18nRepository i18nRepository;
    private static final String DEFAULT_LOCALE_CODE = "en";

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        I18nMessage i18nMessage = i18nRepository.findByConstantAndLang(code, locale.getLanguage());
        if (i18nMessage == null) {
            i18nMessage = i18nRepository.findByConstantAndLang(code, DEFAULT_LOCALE_CODE);
        }
        return i18nMessage != null ? MessageFormat.format(i18nMessage.getTranslation(), args) : defaultMessage;
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return getMessage(code, args, "", locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        String[] codes = resolvable.getCodes();
        for (String code : codes) {
            String message = getMessage(code, resolvable.getArguments(), locale);
            if (message != null) {
                return message;
            }
        }
        return null;
    }

}
