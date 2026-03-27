package com.example.springphone.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springphone.dto.PurchaseResponse;
import com.example.springphone.entity.AppEntity;
import com.example.springphone.entity.PurchaseEntity;
import com.example.springphone.entity.UserEntity;
import com.example.springphone.repository.AppRepository;
import com.example.springphone.repository.PurchaseRepository;
import com.example.springphone.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppRepository appRepository;

    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseServiceImpl(purchaseRepository, userRepository, appRepository);
    }

    @Test
    void purchaseAppShouldSavePurchaseWhenUserAndAppExist() {
        UserEntity user = createUser(1L, "alice");
        AppEntity app = createApp(11L, "TravelMap", "Travel", "2.19");
        PurchaseEntity savedPurchase = new PurchaseEntity();
        savedPurchase.setId(100L);
        savedPurchase.setUser(user);
        savedPurchase.setApp(app);
        savedPurchase.setPurchaseTime(LocalDateTime.of(2026, 3, 27, 10, 30));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(appRepository.findById(11L)).thenReturn(Optional.of(app));
        when(purchaseRepository.existsByUser_IdAndApp_Id(1L, 11L)).thenReturn(false);
        when(purchaseRepository.save(any(PurchaseEntity.class))).thenReturn(savedPurchase);

        PurchaseResponse result = purchaseService.purchaseApp(1L, 11L);

        assertEquals(100L, result.purchaseId());
        assertEquals(11L, result.appId());
        assertEquals("TravelMap", result.appName());
        assertEquals("Travel", result.category());
        assertEquals(new BigDecimal("2.19"), result.price());
        assertEquals(LocalDateTime.of(2026, 3, 27, 10, 30), result.purchaseTime());

        ArgumentCaptor<PurchaseEntity> captor = ArgumentCaptor.forClass(PurchaseEntity.class);
        verify(purchaseRepository).save(captor.capture());
        assertEquals(user, captor.getValue().getUser());
        assertEquals(app, captor.getValue().getApp());
        assertNotNull(captor.getValue().getPurchaseTime());
    }

    @Test
    void purchaseAppShouldThrowWhenAppAlreadyPurchased() {
        UserEntity user = createUser(1L, "alice");
        AppEntity app = createApp(11L, "TravelMap", "Travel", "2.19");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(appRepository.findById(11L)).thenReturn(Optional.of(app));
        when(purchaseRepository.existsByUser_IdAndApp_Id(1L, 11L)).thenReturn(true);

        IllegalStateException exception =
            assertThrows(IllegalStateException.class, () -> purchaseService.purchaseApp(1L, 11L));

        assertEquals("User 1 already purchased app 11", exception.getMessage());
        verify(purchaseRepository, never()).save(any(PurchaseEntity.class));
    }

    @Test
    void getPurchasedAppsShouldReturnPurchasesOrderedByTime() {
        UserEntity user = createUser(1L, "alice");
        PurchaseEntity newer = createPurchase(
            2L, user, createApp(11L, "TravelMap", "Travel", "2.19"), LocalDateTime.of(2026, 3, 27, 11, 0)
        );
        PurchaseEntity older = createPurchase(
            1L, user, createApp(3L, "NoteNest", "Productivity", "1.99"), LocalDateTime.of(2026, 3, 26, 9, 0)
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(purchaseRepository.findByUser_IdOrderByPurchaseTimeDesc(1L)).thenReturn(List.of(newer, older));

        List<PurchaseResponse> result = purchaseService.getPurchasedApps(1L);

        assertEquals(2, result.size());
        assertEquals("TravelMap", result.get(0).appName());
        assertEquals("NoteNest", result.get(1).appName());
    }

    @Test
    void getPurchasedAppsShouldThrowWhenUserDoesNotExist() {
        when(userRepository.findById(42L)).thenReturn(Optional.empty());

        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> purchaseService.getPurchasedApps(42L));

        assertEquals("User not found: 42", exception.getMessage());
        verify(purchaseRepository, never()).findByUser_IdOrderByPurchaseTimeDesc(42L);
    }

    private UserEntity createUser(Long id, String username) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(username + "@example.com");
        return user;
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

    private PurchaseEntity createPurchase(Long id, UserEntity user, AppEntity app, LocalDateTime purchaseTime) {
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setId(id);
        purchase.setUser(user);
        purchase.setApp(app);
        purchase.setPurchaseTime(purchaseTime);
        return purchase;
    }
}
