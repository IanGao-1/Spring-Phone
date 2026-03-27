package com.example.springphone.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springphone.dto.AppResponse;
import com.example.springphone.entity.AppEntity;
import com.example.springphone.entity.UserEntity;
import com.example.springphone.repository.AppRepository;
import com.example.springphone.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppServiceImplTest {

    @Mock
    private AppRepository appRepository;

    @Mock
    private UserRepository userRepository;

    private AppServiceImpl appService;

    @BeforeEach
    void setUp() {
        appService = new AppServiceImpl(appRepository, userRepository);
    }

    @Test
    void browseAppsShouldSearchByNameAndCategoryWhenBothFiltersArePresent() {
        AppEntity app = createApp(3L, "NoteNest", "Productivity", "1.99");
        when(appRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByNameAsc("note", "Productivity"))
            .thenReturn(List.of(app));

        List<AppResponse> result = appService.browseApps(" note ", "Productivity");

        assertEquals(1, result.size());
        assertEquals("NoteNest", result.getFirst().name());
        assertEquals("Productivity", result.getFirst().category());
    }

    @Test
    void browseAppsShouldReturnAllAppsSortedByNameWhenNoFiltersArePresent() {
        AppEntity zeta = createApp(2L, "Zeta Notes", "Productivity", "0.99");
        AppEntity alpha = createApp(1L, "Alpha Weather", "Utilities", "0.00");
        when(appRepository.findAll()).thenReturn(List.of(zeta, alpha));

        List<AppResponse> result = appService.browseApps(null, null);

        assertEquals(2, result.size());
        assertEquals("Alpha Weather", result.get(0).name());
        assertEquals("Zeta Notes", result.get(1).name());
    }

    @Test
    void browseAvailableAppsForUserShouldQueryAvailableAppsByName() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        AppEntity app = createApp(11L, "TravelMap", "Travel", "2.19");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(appRepository.findAvailableAppsForUserByName(1L, "map")).thenReturn(List.of(app));

        List<AppResponse> result = appService.browseAvailableAppsForUser(1L, " map ", null);

        assertEquals(1, result.size());
        assertEquals("TravelMap", result.getFirst().name());
        verify(appRepository).findAvailableAppsForUserByName(1L, "map");
        verify(appRepository, never()).findAvailableAppsForUserByCategory(1L, "map");
    }

    @Test
    void browseAvailableAppsForUserShouldThrowWhenUserDoesNotExist() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> appService.browseAvailableAppsForUser(99L, null, null));

        assertEquals("User not found: 99", exception.getMessage());
        verify(appRepository, never()).findAvailableAppsForUser(99L);
        verify(appRepository, never()).findAvailableAppsForUserByName(anyLong(), anyString());
    }

    private AppEntity createApp(Long id, String name, String category, String price) {
        AppEntity app = new AppEntity();
        app.setId(id);
        app.setName(name);
        app.setCategory(category);
        app.setPrice(new BigDecimal(price));
        app.setDescription(name + " description");
        return app;
    }
}
