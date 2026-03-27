package com.example.springphone.service;

import com.example.springphone.dto.AppResponse;
import java.util.List;

public interface AppService {

    List<AppResponse> browseApps(String name, String category);

    List<AppResponse> browseAvailableAppsForUser(Long userId, String name, String category);
}
