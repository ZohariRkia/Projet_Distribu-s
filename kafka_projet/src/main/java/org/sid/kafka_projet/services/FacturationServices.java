package org.sid.kafka_projet.services;

import com.opencsv.CSVWriter;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.sid.kafka_projet.entites.Facturation;
import org.sid.kafka_projet.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.System.*;

@Service
public class FacturationServices {
    @Autowired
    private ConsumerRepository consumerRepository;


    private static final String DELIMITER = ",";
    private static final String SEPARATOR = "\n";
    private static final String HEADER = "ID, Name, Price";


    @Bean

    public Consumer<Facturation> pageEventConsumer () throws IOException {


                return (input)->{
                    consumerRepository.save(new Facturation(input.getId(),input.getName(), input.getPrice()));
                    out.println("****************************");
                   // System.out.println(input.toString());
                    out.println("****************************");
                    List bookList = new ArrayList();
                    FileWriter file = null;
                    bookList.add(new Facturation(input.getId(),input.getName(), input.getPrice()));
                    //Ajouter l'en-tête
                    try {
                        file = new FileWriter("C:/Users/Administrateur/IdeaProjects/Projet/kafka_projet/src/main/java/org/sid/kafka_projet/file/output.csv",true);

                        //file.append(HEADER);
                        file.append(SEPARATOR);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try
                    {

                        //Itérer bookList
                        Iterator it = bookList.iterator();
                        while(it.hasNext())
                        {
                            Facturation b = (Facturation) it.next();
                            file.append(String.valueOf(b.getId()));
                            file.append(DELIMITER);
                            file.append(b.getName());
                            file.append(DELIMITER);
                            file.append(String.valueOf(b.getPrice()));
                            file.append(SEPARATOR);
                        }

                        file.close();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }




                };
        }

        @Bean
        public Supplier<Facturation> pageEventSupplier(){
            return ()-> new Facturation(null, Math.random()>0.5?"F1":"F2", new Random().nextInt(9000));
        }
    //elle reçoie en input un stream de type kstream

    @Bean

    public Function<KStream<String,Facturation>, KStream<String, Long>> KStreamFunctionClientNumber(){
        return (input)->{
            return input
                    .filter((k,v)->v.getPrice()>100)
                    .map((k,v)->new KeyValue<>(v.getName(),0L))
                    .groupBy((k,v)->k, Grouped.with(Serdes.String(),Serdes.Long()))
                    .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                    .count(Materialized.as("facture-count"))
                    .toStream()
                    .map((k,v)->new KeyValue<>("=>"+k.window().startTime()+k.window().endTime(),v));
        };
    }

 // @Bean
    /*
    public Function<KStream<String,Facturation>, KStream<String, Long>> KStreamFunctionClientNumber(){
        return (input)->{
            return input
                    .filter((k,v)->v.getPrice()>100)
                    .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                    .count(Materialized.as("facture-count"))
                    .toStream()
                    .map((k,v)->new KeyValue<>("=>"+k.window().startTime()+k.window().endTime(),v));
        };
    }*/


    }


