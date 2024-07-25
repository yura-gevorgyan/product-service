package org.example.productservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", url = "${order.service.url}")
public interface FeignClientOrder {

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable("id") int id);

}
