package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        fillData();
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getSearchables() {
        Collection<Searchable> all = new ArrayList<>();
        all.addAll(products.values());
        all.addAll(articles.values());
        return all;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    private void fillData() {
        Product product1 = new FixPriceProduct(UUID.randomUUID(), "Колбаса");
        products.put(product1.getId(), product1);

        Product product2 = new FixPriceProduct(UUID.randomUUID(), "Окорочка");
        products.put(product2.getId(), product2);

        Product product3 = new FixPriceProduct(UUID.randomUUID(), "Зерно");
        products.put(product3.getId(), product3);

        Product product4 = new FixPriceProduct(UUID.randomUUID(), "Сахар");
        products.put(product4.getId(), product4);

        Product product5 = new SimpleProduct(UUID.randomUUID(), "Халва", 200);
        products.put(product5.getId(), product5);

        Product product6 = new DiscountedProduct(UUID.randomUUID(), "Батон", 150, 10);
        products.put(product6.getId(), product6);

        Article article1 = new Article(UUID.randomUUID(), "Скидка", "В нашем магазине грандиозная скидка на батон.");
        articles.put(article1.getId(), article1);

        Article article2 = new Article(UUID.randomUUID(), "Новые товары", "С сегодняшнего дня в ассортименте колбаса, зерно и сахар.");
        articles.put(article2.getId(), article2);
    }
}
