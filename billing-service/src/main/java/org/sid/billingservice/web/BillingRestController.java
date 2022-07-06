package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.Facture;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import org.springframework.data.domain.Pageable;

@RestController
@CrossOrigin("*")
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    //injection des dépendances
    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

   /* @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name="id") Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi -> {
            Product product = productItemRestClient.getProductById(pi.getProductID());
            //pi.setProduct(product);
            pi.setProductName(product.getName());
        });
        return bill;
    }*/
    public double price=0;
    public double qte=0;
    @GetMapping(path = "/fullBill")
    public Page<Facture> getBills(Pageable pageable){
        ArrayList<Facture> bills=new ArrayList<>();

        billRepository.findAll().forEach(b -> {
            Customer customer = customerRestClient.getCustomerById(b.getCustomerID());
            System.out.println("AAAAAAAAAAAAA"+b.getCustomerID());
            price=0;
            qte=0;
            b.getProductItems().forEach(p -> {
                price=price+p.getPrice();
                qte=qte+p.getQuantity();
            });
            bills.add(new Facture(b.getId(), customer.getName(), price,qte));
            System.out.println("************"+customer.getName()+"********");
        });
        return new PageImpl<Facture>(bills, pageable, bills.size());
    }

}
