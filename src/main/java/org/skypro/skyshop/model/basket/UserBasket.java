package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.service.BasketService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserBasket {
    private final List<BasketItem> items;
    private int total = 0;

    public UserBasket(List<BasketItem> items) {
        this.items = items;
        calculateTotal();
    }
    public void calculateTotal() {
        total = items.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getAmount())
                .sum();
    }

    public Collection<BasketItem> getProducts() {
        return items;
    }

    public int getTotal() {
        return total;
    }
}
