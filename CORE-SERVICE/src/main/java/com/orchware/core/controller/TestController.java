package com.orchware.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController("TestController")
//@RequestMapping("/test")
public class TestController {

    @GetMapping("/green")
    public String test() {
        return "This is the Test Green";
    }
}
