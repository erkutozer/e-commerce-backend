package com.erkutozer.services;

import com.erkutozer.dto.CreateOrderRequestDto;
import com.erkutozer.dto.OrderItemRequestDto;
import com.erkutozer.dto.OrderResponseDto;
import com.erkutozer.entity.Order;
import com.erkutozer.entity.OrderItem;
import com.erkutozer.entity.Product;
import com.erkutozer.exception.InsufficientStockException;
import com.erkutozer.exception.ResourceNotFoundException;
import com.erkutozer.repository.OrderRepository;
import com.erkutozer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto request){
        Map<Long, Integer> quantities = new LinkedHashMap<>();

        for(OrderItemRequestDto item : request.items()){
            quantities.merge(item.productId(), item.quantity(), Integer::sum);

        }

        Map<Long, Product> products = productRepository.findAllById(quantities.keySet()).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<Long> missing = quantities.keySet().stream()
                .filter(id -> !products.containsKey(id))
                .toList();
        if (!missing.isEmpty()){
            throw new ResourceNotFoundException("Ürün bulunamadı: " + missing);
        }

        Order order = new Order();
        order.setOrderNumber("Sipariş Numarası : " + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        order.setFullName(request.fullName().trim());
        order.setEmail(request.email().trim());
        order.setPhone(request.phone().trim());
        order.setCity(request.city().trim());
        order.setAddress(request.address().trim());

        BigDecimal total = BigDecimal.ZERO;


        for (Map.Entry<Long, Integer> entry : quantities.entrySet()){
            Product product = products.get(entry.getKey());
            int quantity = entry.getValue();

            if (productRepository.decreaseStok(product.getId(),quantity) == 0){
                throw new InsufficientStockException("Yetersiz Stok " + product.getTitle());
            }
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setProductTitle(product.getTitle());
            item.setUnitPrice(product.getPrice());
            item.setQuantity(quantity);
            order.addItem(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        order.setTotalAmount(total);
        return OrderResponseDto.from(orderRepository.save(order));

    }
}
