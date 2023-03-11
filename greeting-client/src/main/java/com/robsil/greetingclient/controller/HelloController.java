package com.robsil.greetingclient.controller;

import com.robsil.greetingclient.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/greetings")
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/greet")
    public String getGreeting(@RequestParam String name) {
        return helloService.getGreeting(name);
    }

}
