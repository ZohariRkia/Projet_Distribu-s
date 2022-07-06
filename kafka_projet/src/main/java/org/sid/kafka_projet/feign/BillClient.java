package org.sid.kafka_projet.feign;

import org.sid.kafka_projet.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@FeignClient(name="PRODUCT-SERVICE")
public interface BillClient {
    @GetMapping(path = "/products/{id}")
    public Product pageProducts();


}
