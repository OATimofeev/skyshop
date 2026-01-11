package org.skypro.skyshop.exception;

import org.skypro.skyshop.model.product.Product;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException() {
        super("Нет в наличии!");
    }
}
