package org.sid.customerservice.repository;

import org.sid.customerservice.entites.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.data.domain.Pageable;


@CrossOrigin("*")


@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @RestResource(path = "/byNamePage")
    public Page<Customer> findByNameContains(@Param("mc") String des, Pageable pageable);

}
