package com.orchware.commons.module.i18n.repository;

import com.orchware.commons.module.i18n.model.I18nMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface I18nRepository extends JpaRepository<I18nMessage, Long> {

    I18nMessage findByConstantAndLang(String constant, String lang);

    List<I18nMessage> findByConstant(String constant);
}
