package com.zcy.sys.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignApiController {

    @PostMapping("/sign")
    public String getSign() {
        return "sign";
    }
}
