package org.sid.billingservice.feign;

import org.sid.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {
       //je demande à open feign si j'appelle la méthode getCustomerById tu m'envoie une requette avec get
       @GetMapping(path = "/customers/{id}")

        public  Customer getCustomerById(@PathVariable(name="id") Long id);
}
