package org.skypro.skyshop.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Getter
public class StorageService {

    private final Map<UUID, Product> productMap;
    private final Map<UUID, Article> articleMap;

    public StorageService() {
        productMap = new HashMap<>();
        articleMap = new HashMap<>();
        generateData();
    }

    public List<Searchable> getAll() {
        return Stream
                .concat(productMap.values().stream(), articleMap.values().stream())
                .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productMap.get(id));
    }

    private void generateData() {
        putProduct(new SimpleProduct(UUID.randomUUID(), "Банан", 50));
        putProduct(new SimpleProduct(UUID.randomUUID(), "Ряженка", 80));
        putProduct(new FixPriceProduct(UUID.randomUUID(), "Яблоко"));
        putProduct(new DiscountedProduct(UUID.randomUUID(), "Молоток", 270, 25));

        putArticle(new Article(UUID.randomUUID(), "Выращивание вшей на бровях", "Тут есть текст????"));
        putArticle(new Article(UUID.randomUUID(), "Промывка носа пескоструем на лошадях", "Не буду комментировать"));
    }

    private void putProduct(Product product) {
        productMap.put(product.getId(), product);
    }

    private void putArticle(Article article) {
        articleMap.put(article.getId(), article);
    }
}
