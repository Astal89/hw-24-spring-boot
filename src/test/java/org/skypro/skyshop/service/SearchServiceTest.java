package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private Collection<Searchable> searchables;

    @BeforeEach
    void createSearchables() {
        Searchable product1 = new FixPriceProduct(UUID.randomUUID(), "TestProduct");
        Searchable product2 = new FixPriceProduct(UUID.randomUUID(), "Кефир");
        Searchable article = new Article(UUID.randomUUID(), "Новые продукты", "поступление новых продуктов");
        searchables = List.of(product1, product2, article);
    }

    // пустое хранилище
    @Test
    void shouldReturnEmptyResultWhenStorageIsEmpty() {
        // Настраиваем Mock что бы при обращении к методу getSearchables
        // выдавался пустой список
        when(storageService.getSearchables()).thenReturn(List.of());

        // Вызываем метод поиска сервиса. Внутри метода произойдет обращение к
        // ранее настроенному Mock, который вернет пустой список
        Collection<SearchResult> result = searchService.search("пирог");

        // Проверяем, что result на самом деле пустой
        assertThat(result).isEmpty();
        // Проверяем, что getSearchables вызвался хотя-бы один раз
        verify(storageService, times(1)).getSearchables();
    }

    // Поиск в случае, если объекты в StorageService есть, но нет подходящего.
    @Test
    void shouldReturnEmptyResultWhenNothingMatches() {
        when(storageService.getSearchables()).thenReturn(searchables.stream().toList());
        Collection<SearchResult> result = searchService.search("хлеб");
        assertThat(result).isEmpty();
        verify(storageService, times(1)).getSearchables();
    }

    // Поиск, когда есть подходящий объект в StorageService
    @Test
    void shouldReturnMatchingResult() {
        when(storageService.getSearchables()).thenReturn(searchables.stream().toList());
        Collection<SearchResult> result = searchService.search("Test");
        assertThat(result).hasSize(1);
    }
}
