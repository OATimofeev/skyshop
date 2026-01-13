package org.skypro.skyshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTests {

    @Mock
    private StorageService storageService;
    @Mock
    private ProductBasket productBasket;
    @InjectMocks
    private BasketService basketService;

    private final UUID uuid = UUID.randomUUID();

    @Test
    @DisplayName("Исключение при добавлении несуществующего товара в корзину")
    public void addingNotExistItemTest() {
        when(storageService.getProductById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchProductException.class, () -> basketService.addItem(uuid));
    }

    @Test
    @DisplayName("Метод addProduct при добавлении существующего товара в корзину")
    public void successAddingTest() {
        when(storageService.getProductById(uuid)).thenReturn(Optional.of(new SimpleProduct(uuid, "TestProduct", 20)));

        basketService.addItem(uuid);

        verify(productBasket).addProduct(uuid);
    }

    @Test
    @DisplayName("Метод getUserBacket возвращает пустую корзину при пустом ProductBasket")
    public void getEmptyBasketTest() {
        when(productBasket.getBasket()).thenReturn(Map.of());

        UserBasket userBasket = basketService.getUserBasket();

        assertTrue(userBasket.getItemList().isEmpty());
        assertEquals(0, userBasket.getTotal());
    }

    @Test
    @DisplayName("Метод getUserBacket возвращает валидную корзину при пустом ProductBasket")
    public void getBasketTest() {
        UUID additionalUuid = UUID.randomUUID();

        when(productBasket.getBasket()).thenReturn(Map.of(uuid, 1, additionalUuid, 3));
        when(storageService.getProductById(uuid)).thenReturn(Optional.of(new SimpleProduct(uuid, "test1", 10)));
        when(storageService.getProductById(additionalUuid)).thenReturn(Optional.of(new SimpleProduct(additionalUuid, "test2", 20)));

        UserBasket basket = basketService.getUserBasket();

        Assertions.assertEquals(70, basket.getTotal());
        Assertions.assertEquals(2, basket.getItemList().size());
    }
}
