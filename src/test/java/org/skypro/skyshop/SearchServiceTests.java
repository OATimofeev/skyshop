package org.skypro.skyshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTests {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private final String testId = "bb5f9da7-f6b3-4520-8806-6b4171072bca";
    private final List<Searchable> mockedData = List.of(new SimpleProduct(UUID.fromString(testId), "TestProduct", 123), new Article(UUID.randomUUID(), "TestArticle", "TestText"));

    @Test
    @DisplayName("Нет объектов в storageService")
    public void emptyStorageTest() {
        when(storageService.getAll()).thenReturn(List.of());

        Assertions.assertTrue(searchService.search("123").isEmpty());
    }

    @Test
    @DisplayName("Нет подходящего среди объектов в storageService")
    public void notFoundTest() {
        when(storageService.getAll()).thenReturn(mockedData);

        Assertions.assertTrue(searchService.search("NotTest").isEmpty());
    }

    @Test
    @DisplayName("Найден подходящий среди объектов в storageService")
    public void foundedTest() {
        when(storageService.getAll()).thenReturn(mockedData);

        Assertions.assertEquals(1, searchService.search("TestProduct").size());
    }

    @Test
    @DisplayName("Найдены все подходящие по частичному совпадению среди объектов в storageService")
    public void foundedParticalTest() {
        when(storageService.getAll()).thenReturn(mockedData);

        Assertions.assertEquals(2, searchService.search("est").size());
    }

    @Test
    @DisplayName("Полная проверка соответствия объекта объектов в storageService")
    public void foundedFullCheckTest() {
        when(storageService.getAll()).thenReturn(mockedData);

        Assertions.assertEquals(
                List.of(new SearchResult(testId, "TestProduct - PRODUCT", "PRODUCT")),
                searchService.search("TestProduct"));
    }
}
