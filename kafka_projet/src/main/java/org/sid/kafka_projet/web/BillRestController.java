package org.sid.kafka_projet.web;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.sid.kafka_projet.entites.Facturation;
import org.sid.kafka_projet.feign.BillClient;
import org.sid.kafka_projet.model.Product;
import org.sid.kafka_projet.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BillRestController {
    @Autowired
    public ConsumerRepository consumerRepository;
    /*
    @GetMapping(path="/Fullproducts")

    public PagedModel<Product> getBill(){
        return BillClient.pageProducts();

    }*/
    @GetMapping(path = "/factures")
    public List<Facturation> getFacturation(){
        return consumerRepository.findAll();

    }
    @GetMapping(path = "/factures/{id}")
    public Facturation getFacturation(@PathVariable(name="id") Long id){
        return  consumerRepository.findById(id).get();
    }
    @Autowired private InteractiveQueryService interactiveQueryService;
    @GetMapping(path = "/analytics",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String, Long>> analytics(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence->{
                    Map<String,Long> stringLongMap=new HashMap<>();
                    ReadOnlyWindowStore<String, Long> stats =
                            interactiveQueryService.getQueryableStore("facture-count", QueryableStoreTypes.windowStore());
                    Instant now=Instant.now();
                    Instant from=now.minusMillis(5000);
                    KeyValueIterator<Windowed<String>, Long> fetchAll = stats.fetchAll(from, now);
                    while (fetchAll.hasNext()){
                        KeyValue<Windowed<String>, Long> next = fetchAll.next();
                        stringLongMap.put(next.key.key(),next.value);
                        System.out.println("***************************"+next.key.key()+" "+next.value+"***************************");
                    }
                    return stringLongMap;
                }).share();
    }

}
