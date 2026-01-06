package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private int price;
    private int discount;

    public DiscountedProduct(UUID id, String name, int price, int discount) {
        super(id, name);
        checkPriceAndDiscount(price, discount);
        this.price = price;
        this.discount = discount;
    }

    @Override
    public Integer getPrice() {
        return price * (100 - discount) / 100;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return "%s: %d (%d%%)".formatted(name, price, discount);
    }

    private void checkPriceAndDiscount(int price, int discount) throws IllegalArgumentException {
        if (price <= 0) {
            throw new IllegalArgumentException("Цена должна быть > 0");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне 0-100");
        }
    }
}
