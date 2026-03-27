package com.example.springphone.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springphone.dto.AppResponse;
import com.example.springphone.service.AppService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AppController.class)
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppService appService;

    @Test
    void browseAppsShouldReturnFilteredApps() throws Exception {
        when(appService.browseApps("fit", "Health")).thenReturn(List.of(
            new AppResponse(2L, "FitTrack Pro", "Health", new BigDecimal("3.99"), "Tracks daily fitness goals.")
        ));

        mockMvc.perform(get("/api/apps")
                .param("name", "fit")
                .param("category", "Health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(2))
            .andExpect(jsonPath("$[0].name").value("FitTrack Pro"))
            .andExpect(jsonPath("$[0].category").value("Health"))
            .andExpect(jsonPath("$[0].price").value(3.99));
    }
}
