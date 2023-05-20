package com.example.OrderService.service;

import com.example.OrderService.dto.InventoryResponse;
import com.example.OrderService.dto.OrderLineItemsDto;
import com.example.OrderService.dto.OrderRequest;
import com.example.OrderService.model.Order;
import com.example.OrderService.model.OrderLineItems;
import com.example.OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrdernumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::maptoDto).collect(Collectors.toList());
        order.setOrderLineIteamsList(orderLineItems);
        List<String> skucodes = order.getOrderLineIteamsList().stream().map(OrderLineItems::getSkuCode).toList();

       InventoryResponse [] inventoryResponsesArray =  webClientBuilder.build().get().uri("http://localhost:8092/api/inventory",
                       uriBuilder -> uriBuilder.queryParam("skuCode", skucodes).build())
                        .retrieve().bodyToMono(InventoryResponse[].class).block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock);

       if(allProductsInStock){
           orderRepository.save(order);
           log.info("######################## order placed ########################");
       }else {
           log.info("@@@@@@@@@@@@@@@@@@@@@@@@ order not placed @@@@@@@@@@@@@@@@@@@");
          throw new IllegalArgumentException("product is not in stock please try again later");

       }

    }

    private OrderLineItems maptoDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }


}
