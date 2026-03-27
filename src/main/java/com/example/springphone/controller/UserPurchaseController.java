package com.example.springphone.controller;

import com.example.springphone.dto.AppResponse;
import com.example.springphone.dto.PurchaseRequest;
import com.example.springphone.dto.PurchaseResponse;
import com.example.springphone.service.AppService;
import com.example.springphone.service.PurchaseService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{userId}")
public class UserPurchaseController {

    private final AppService appService;
    private final PurchaseService purchaseService;

    public UserPurchaseController(AppService appService, PurchaseService purchaseService) {
        this.appService = appService;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/apps/available")
    public List<AppResponse> browseAvailableApps(
        @PathVariable Long userId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category
    ) {
        return appService.browseAvailableAppsForUser(userId, name, category);
    }

    @GetMapping("/purchases")
    public List<PurchaseResponse> getPurchasedApps(@PathVariable Long userId) {
        return purchaseService.getPurchasedApps(userId);
    }

    @PostMapping("/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponse purchaseApp(
        @PathVariable Long userId,
        @RequestBody PurchaseRequest request
    ) {
        return purchaseService.purchaseApp(userId, request.appId());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(IllegalArgumentException exception) {
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflict(IllegalStateException exception) {
        return Map.of("error", exception.getMessage());
    }
}
