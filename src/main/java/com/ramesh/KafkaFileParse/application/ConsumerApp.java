package com.ramesh.KafkaFileParse.application;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ConsumerApp {
    public static void main(String[] args) {
        runConsumer();
    }


    static void runConsumer() {
        Consumer<String, byte[]> consumer = ConsumerFactory.createConsumer();

//        while (true) {
//            ConsumerRecords<String, byte[]> records = consumer.poll(1000);
//            for (ConsumerRecord<String, byte[]> record : records) {
//                System.out.printf("offset = %d, key = %s, value = %s\n",
//                        record.offset(), record.key(), record.value());
//                writeBytesToFileClassic(record.value(), "C:/Kafka/Service/KafkaFileParse/ConsumerDataset" + record.key());
//                //consumer.commitAsync();
//            }
//            consumer.commitAsync();
//        }



        int noMessageToFetch = 0;
        while (true) {
            final ConsumerRecords<String, byte[]> consumerRecords = consumer.poll(1000);
            System.out.println("consumerRecords.count="+consumerRecords.count());
            if (consumerRecords.count() == 0) {
                noMessageToFetch++;
                if (noMessageToFetch > 100)
                    break;
                else
                    continue;
            }
            System.out.println("Consumer record="+consumerRecords);

            consumerRecords.forEach(record -> {
//                System.out.println(record.partition());
//                System.out.println(record.offset());

                System.out.println("File name : " + record.key() );
                writeBytesToFileClassic(record.value(), "C:/Kafka/Service/KafkaFileParse/ConsumerDataset" + record.key());
            });

            consumer.commitAsync();
        }
        System.out.println("Files received Successful");
        consumer.close();






    }

    private static void writeBytesToFileClassic(byte[] byteFile, String fileDest) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileDest);
            fileOutputStream.write(byteFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
