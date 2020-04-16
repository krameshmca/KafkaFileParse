package com.ramesh.KafkaFileParse.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

//@Configuration
public class KafkaConfig {
    private final Boolean isAsync;
    private final KafkaProducer<String , byte[]> producer;

    public  KafkaConfig(String topic, Boolean isAsync) {
      //  Map<String, Object> config = new HashMap<>();
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 536870912);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,536870912);
       // props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        //config.put(ProducerConfig.LINGER_MS_CONFIG, "100");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "536870912");
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,120000);
        producer = new KafkaProducer<String, byte[]>(props);
        this.isAsync = isAsync;
    }
    public void sendMessage(String key, byte[] value) {

        try {
            if (isAsync) {

            } else {
                producer.send(new ProducerRecord<String, byte[]>("document", key, value)).get();
                System.out.println("Sent message : ( " + key + "," + value + " )");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
//    @Bean
//    public KafkaTemplate<String, byte[]> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
}