package org.skypro.skyshop.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShopError {
    private final String code;
    private final String message;
}
