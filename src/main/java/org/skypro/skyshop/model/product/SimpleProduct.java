package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private int price;

    public SimpleProduct(UUID id, String name, int price) {
        super(id, name);
        checkPrice(price);
        this.price = price;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public String toString() {
        return "%s: %d".formatted(name, price);
    }

    private void checkPrice(int price) throws IllegalArgumentException {
        if (price <= 0) {
            throw new IllegalArgumentException("Цена должна быть > 0");
        }
    }
}
