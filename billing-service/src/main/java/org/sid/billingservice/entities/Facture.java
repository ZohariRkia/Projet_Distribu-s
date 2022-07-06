package org.sid.billingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Facture {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long ID;
        private String name;
        private double price;
        private double quantity;

}

