package org.skypro.skyshop.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    public void addItem(UUID id) {
        if (storageService.getProductById(id).isEmpty()) {
            throw new NoSuchProductException();
        }
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {

        Map<UUID, Integer> basket =   productBasket.getBasket();
        return new UserBasket(
                productBasket.getBasket()
                        .entrySet()
                        .stream()
                        .map(entry -> storageService.getProductById(entry.getKey())
                                .map(product -> new BasketItem(product, entry.getValue()))
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .toList());
    }
}
