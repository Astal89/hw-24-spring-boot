package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private int basePrice;
    private int discount;
    public DiscountedProduct(UUID id, String name, int basePrice, int discount) {
        super(id, name);
        if(basePrice <= 0) {
            throw new IllegalArgumentException("Стоимость продукта должна быть больше нуля.");
        }
        if(discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Размер скидки на продукт должен быть в диапазоне от 0 до 100.");
        }
        this.basePrice = basePrice;
        this.discount = discount;
    }

    @Override
    public int getPrice() {
        return basePrice * (100 - discount) / 100;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice() + "(" + discount + "%)";
    }
}

