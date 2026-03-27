package com.example.springphone.service.impl;

import com.example.springphone.dto.AppResponse;
import com.example.springphone.entity.AppEntity;
import com.example.springphone.repository.AppRepository;
import com.example.springphone.repository.UserRepository;
import com.example.springphone.service.AppService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;
    private final UserRepository userRepository;

    public AppServiceImpl(AppRepository appRepository, UserRepository userRepository) {
        this.appRepository = appRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<AppResponse> browseApps(String name, String category) {
        List<AppEntity> apps;

        if (hasText(name) && hasText(category)) {
            apps = appRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByNameAsc(name.trim(), category.trim());
        } else if (hasText(name)) {
            apps = appRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name.trim());
        } else if (hasText(category)) {
            apps = appRepository.findByCategoryIgnoreCaseOrderByNameAsc(category.trim());
        } else {
            apps = appRepository.findAll().stream().sorted((left, right) -> left.getName().compareToIgnoreCase(right.getName())).toList();
        }

        return apps.stream().map(this::toResponse).toList();
    }

    @Override
    public List<AppResponse> browseAvailableAppsForUser(Long userId, String name, String category) {
        userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        List<AppEntity> apps;
        if (hasText(name) && hasText(category)) {
            apps = appRepository.findAvailableAppsForUserByNameAndCategory(userId, name.trim(), category.trim());
        } else if (hasText(name)) {
            apps = appRepository.findAvailableAppsForUserByName(userId, name.trim());
        } else if (hasText(category)) {
            apps = appRepository.findAvailableAppsForUserByCategory(userId, category.trim());
        } else {
            apps = appRepository.findAvailableAppsForUser(userId);
        }

        return apps.stream().map(this::toResponse).toList();
    }

    private AppResponse toResponse(AppEntity app) {
        return new AppResponse(
            app.getId(),
            app.getName(),
            app.getCategory(),
            app.getPrice(),
            app.getDescription()
        );
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
