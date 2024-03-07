package com.orchware.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/orchestrationFallBack")
    public String orchestrationFallBackMethod() {
        return "Core app is closed for maintenance.";
    }

    @GetMapping("/loginServiceFallBack")
    public String loginServiceFallBack() {
        return "Login application is closed for maintenance.";
    }

    @GetMapping("/accountServiceFallBack")
    public String accountServiceFallBack() {
        return "Account application is closed for maintenance.";
    }

    @GetMapping("/commonsServiceFallBack")
    public String commonsServiceFallBack() {
        return "Commons application is closed for maintenance.";
    }

}
