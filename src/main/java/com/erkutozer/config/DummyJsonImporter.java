package com.erkutozer.config;


import com.erkutozer.entity.Product;
import com.erkutozer.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.import.dummyjson", havingValue = "true")
public class DummyJsonImporter implements CommandLineRunner {

    private static final String URL = "https://dummyjson.com/products?limit=0&select=title,description,price,stock,thumbnail";
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        DummyProductResponse response = RestClient.create()
                .get()
                .uri(URL)
                .retrieve()
                .body(DummyProductResponse.class);

        if (response == null || response.products() == null) {
            log.warn("dummyjson'dan ürünler çekilemedi.");
            return;
        }


        Set<String> existingTitles = productRepository.findAll().stream()
                .map(p -> p.getTitle().toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
        List<Product> toSave = new ArrayList<>();


        for (DummyProducts dp : response.products()){
            if (dp.title() == null || dp.price() == null){
                continue;
            }

            String key = dp.title().toLowerCase(Locale.ROOT);
            if (!existingTitles.add(key)){
                continue;
            }
            Product product = new Product();
            product.setTitle(dp.title());
            product.setDescription(dp.description());
            product.setPrice(dp.price().setScale(2, RoundingMode.HALF_UP));
            product.setStok(dp.stock() != null ? dp.stock() : 0);
            product.setThumbnail(dp.thumbnail());
            toSave.add(product);
        }

        productRepository.saveAll(toSave);
        log.info("dummyjson içe aktarımı bitti: {} yeni ürün eklendi, {} atlandı", toSave.size(), response.products().size() - toSave.size());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DummyProductResponse(List<DummyProducts> products){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DummyProducts(String title, String description, BigDecimal price, Integer stock, String thumbnail){}
}
