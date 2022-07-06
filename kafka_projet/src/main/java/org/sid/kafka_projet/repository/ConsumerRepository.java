package org.sid.kafka_projet.repository;

import org.sid.kafka_projet.entites.Facturation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ConsumerRepository extends JpaRepository <Facturation,Long> {

}
