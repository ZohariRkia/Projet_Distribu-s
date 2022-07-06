package org.sid.kafka_projet;

import org.sid.kafka_projet.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@EnableFeignClients
@SpringBootApplication
public class KafkaProjetApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProjetApplication.class, args);
    }



}
