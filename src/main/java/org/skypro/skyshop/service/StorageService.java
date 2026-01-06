package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {

    private final Map<UUID, Product> productMap;
    private final Map<UUID, Article> articleMap;

    public StorageService() {
        productMap = new HashMap<>();
        articleMap = new HashMap<>();
        generateData();
    }

    public Map<UUID, Product> getProductMap() {
        return productMap;
    }

    public Map<UUID, Article> getArticleMap() {
        return articleMap;
    }

    public List<Searchable> getAll() {
        return Stream
                .concat(productMap.values().stream(), articleMap.values().stream())
                .collect(Collectors.toList());
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
