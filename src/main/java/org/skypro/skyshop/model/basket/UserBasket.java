package org.skypro.skyshop.model.basket;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class UserBasket {

    private final List<BasketItem> itemList;
    private final Integer total;

    public UserBasket(List<BasketItem> itemList) {
        this.itemList = itemList;
        total = itemList
                .stream()
                .mapToInt(item -> item.product().getPrice() * item.count())
                .sum();
    }
}
