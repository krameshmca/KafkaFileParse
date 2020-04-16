package com.ramesh.KafkaFileParse.application;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.FileOutputStream;
import java.io.IOException;

public class NewConsumerApp {
    public static void main(String[] args) {
        readConsumer();
    }

    static void readConsumer() {
        int noMessageToFetch = 0;
        Consumer<String, byte[]> consumer = ConsumerFactory.createConsumer();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(1000);
            System.out.println("consumerRecords.count=" + records.count());
            if (records.count() == 0) {
                noMessageToFetch++;
                if (noMessageToFetch > 100)
                    break;
                else
                    continue;
            }
            System.out.println("Consumer record=" + records);
            for (ConsumerRecord<String, byte[]> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
                writeBytesToFileClassic(record.value(), "C:/Kafka/Service/KafkaFileParse/ConsumerDataset/" + record.key());
                //consumer.commitAsync();
            }
            consumer.commitAsync();
        }
        System.out.println("Files received Successful");
        consumer.close();
    }

    private static void writeBytesToFileClassic(byte[] byteFile, String fileDest) {
        System.out.println("Inside Write bytes");
        System.out.println("Path="+fileDest);
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
