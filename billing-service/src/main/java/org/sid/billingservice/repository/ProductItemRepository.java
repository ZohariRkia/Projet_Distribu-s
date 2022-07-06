package org.sid.billingservice.repository;

import org.sid.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;


@CrossOrigin("*")

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
    //je donne l'id de facture et il me retourne la liste des productitem dans cette facture
    public Collection<ProductItem> findByBillId(Long id);
}
