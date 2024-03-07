package com.orchware.commons.module.i18n.controller;

import com.orchware.commons.module.i18n.model.I18nMessage;
import com.orchware.commons.module.i18n.service.I18nService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("InternationalizationController")
@RequestMapping(value = "/message")
public class InternationalizationController {

    private final I18nService i18nService;

    InternationalizationController(I18nService i18nService) {
        this.i18nService = i18nService;
    }

    /*@GetMapping("/")
    public String greet() {
        return "index";
    }*/

    @PostMapping
    public ResponseEntity<?> save(@RequestBody I18nMessage i18nMessage) {
        return ResponseEntity.ok(i18nService.save(i18nMessage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody I18nMessage i18nMessage, @PathVariable Long id) {
        return ResponseEntity.ok(i18nService.update(i18nMessage, id));
    }

    @Cacheable(value = "i18nStringIdCache", key = "{#id, #lang}")
    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, @RequestParam String lang) {
        return i18nService.getMessage(id, lang);
    }

    @Cacheable(value = "i18nStringConstantCache", key = "{#constant, #lang}")
    @GetMapping("/constant/{constant}")
    public String getByConstant(@PathVariable("constant") String constant, @RequestParam String lang) {
        return i18nService.getMessage(constant, lang);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(i18nService.getAll());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        i18nService.delete(id);
    }

    @DeleteMapping
    public void deleteAll() {
        i18nService.deleteAll();
    }
}
