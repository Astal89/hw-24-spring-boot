package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {
    @Mock
    private StorageService storageService;
    @Mock
    private ProductBasket productBasket;

    @InjectMocks
    private BasketService basketService;

    @Test
    void shouldThrowWhenAddingNonExistentProduct() {
        UUID id = UUID.randomUUID();
        when(storageService.getProductById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.addProduct(id));

        verify(productBasket, never()).addProduct(any());
    }

    @Test
    void shouldAddProductToBasketWhenProductExists() {
        UUID id = UUID.randomUUID();
        Product product = new FixPriceProduct(id, "TestProduct");
        when(storageService.getProductById(id)).thenReturn(Optional.of(product));
        basketService.addProduct(id);
        verify(productBasket, times(1)).addProduct(id);
    }

    @Test
    void shouldReturnEmptyUserBasketWhenProductBasketIsEmpty() {
        when(productBasket.getProducts()).thenReturn(Map.of());
        UserBasket userBasket = basketService.getUserBasket();

        assertThat(userBasket.getProducts()).isEmpty();
        assertThat(userBasket.getTotal()).isZero();
    }

    @Test
    void shouldReturnUserBasketWithProductsWhenProductBasketIsNotEmpty() {
        UUID id = UUID.randomUUID();
        int price = 100;
        int amount = 2;
        Product product = new SimpleProduct(id, "TestProduct", price);

        when(productBasket.getProducts()).thenReturn(Map.of(id, amount));
        when(storageService.getProductById(id)).thenReturn(Optional.of(product));

        UserBasket userBasket = basketService.getUserBasket();
        assertThat(userBasket.getProducts()).hasSize(1);
        assertThat(userBasket.getTotal()).isEqualTo(price * amount);
    }
}
