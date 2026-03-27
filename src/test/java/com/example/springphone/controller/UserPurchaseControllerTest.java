package com.example.springphone.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springphone.dto.AppResponse;
import com.example.springphone.dto.PurchaseResponse;
import com.example.springphone.service.AppService;
import com.example.springphone.service.PurchaseService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserPurchaseController.class)
class UserPurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppService appService;

    @MockBean
    private PurchaseService purchaseService;

    @Test
    void browseAvailableAppsShouldReturnAvailableAppsForUser() throws Exception {
        when(appService.browseAvailableAppsForUser(1L, null, "Games")).thenReturn(List.of(
            new AppResponse(18L, "StreetBall", "Games", new BigDecimal("4.49"), "Arcade basketball game.")
        ));

        mockMvc.perform(get("/api/users/1/apps/available").param("category", "Games"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(18))
            .andExpect(jsonPath("$[0].name").value("StreetBall"))
            .andExpect(jsonPath("$[0].category").value("Games"));
    }

    @Test
    void getPurchasedAppsShouldReturnPurchaseHistory() throws Exception {
        when(purchaseService.getPurchasedApps(1L)).thenReturn(List.of(
            new PurchaseResponse(
                101L,
                11L,
                "TravelMap",
                "Travel",
                new BigDecimal("2.19"),
                LocalDateTime.of(2026, 3, 27, 10, 30)
            )
        ));

        mockMvc.perform(get("/api/users/1/purchases"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].purchaseId").value(101))
            .andExpect(jsonPath("$[0].appId").value(11))
            .andExpect(jsonPath("$[0].appName").value("TravelMap"));
    }

    @Test
    void purchaseAppShouldReturnCreatedWhenPurchaseSucceeds() throws Exception {
        when(purchaseService.purchaseApp(1L, 11L)).thenReturn(
            new PurchaseResponse(
                102L,
                11L,
                "TravelMap",
                "Travel",
                new BigDecimal("2.19"),
                LocalDateTime.of(2026, 3, 27, 10, 45)
            )
        );

        mockMvc.perform(post("/api/users/1/purchases")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                      "appId": 11
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.purchaseId").value(102))
            .andExpect(jsonPath("$.appName").value("TravelMap"));
    }

    @Test
    void purchaseAppShouldReturnConflictWhenAppAlreadyPurchased() throws Exception {
        when(purchaseService.purchaseApp(1L, 11L))
            .thenThrow(new IllegalStateException("User 1 already purchased app 11"));

        mockMvc.perform(post("/api/users/1/purchases")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                      "appId": 11
                    }
                    """))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error").value("User 1 already purchased app 11"));
    }

    @Test
    void getPurchasedAppsShouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        when(purchaseService.getPurchasedApps(99L))
            .thenThrow(new IllegalArgumentException("User not found: 99"));

        mockMvc.perform(get("/api/users/99/purchases"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("User not found: 99"));
    }
}
