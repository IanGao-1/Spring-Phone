package com.example.springphone.controller;

import com.example.springphone.dto.AppResponse;
import com.example.springphone.service.AppService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/apps")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<AppResponse> browseApps(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category
    ) {
        return appService.browseApps(name, category);
    }
}
