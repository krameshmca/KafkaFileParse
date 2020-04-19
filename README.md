# KafkaFileParse
Download Kafka and Zookeeper from below link

https://kafka.apache.org/downloads
https://zookeeper.apache.org/releases.html

Unzip the binary file and Keep in local.

Zookeeper Start:
C:\Kafka\zookeeper-3.6.0\bin> zkserver

Kafka Start:
C:\Kafka\kafka_2.12-2.4.1> .\bin\windows\kafka-server-start.bat .\config\server.properties

Create Toic:
C:\Kafka\kafka_2.12-2.4.1\bin\windows>kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic document

List topic: C:\Kafka\kafka_2.12-2.4.1\bin\windows>kafka-topics.bat --list --zookeeper localhost:2181

Producer: C:\Kafka\kafka_2.12-2.4.1\bin\windows>kafka-console-producer.bat --broker-list localhost:9092 --topic document

Consumer: C:\Kafka\kafka_2.12-2.4.1\bin\windows>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic document

Put file: kafka-console-producer.sh --broker-list localhost:9092 --topic my_topic --new-producer < my_file.txt
kafka-console-producer.sh --broker-list localhost:9092 --topic my_topic < my_file.txt

API URL: http://localhost:8081/kafka/publish

kafka reference:
https://dzone.com/articles/running-apache-kafka-on-windows-os
https://stackoverflow.com/questions/33606287/is-it-possible-to-write-kafka-consumer-received-output-to-a-file-using-java
https://medium.com/workday-engineering/large-message-handling-with-kafka-chunking-vs-external-store-33b0fc4ccf14
https://www.oodlestechnologies.com/blogs/Upload-Files-by-Sending-MultipartFile-Request-In-Java/
https://github.com/karande/kafka-producer-file/blob/master/src/main/java/KafkaFileProducer.java
https://stackoverflow.com/questions/33143743/read-data-from-multipartfile-which-has-csv-uploaded-from-browser
https://medium.com/@malinda0610/apache-kafka-moving-files-from-producer-to-consumer-466c5fbaab1c
https://dzone.com/articles/spring-boot-file-upload-example-with-multipartfile
https://thepracticaldeveloper.com/2018/11/24/spring-boot-kafka-config/
https://www.baeldung.com/spring-kafka
https://dzone.com/articles/magic-of-kafka-with-spring-boot
https://www.confluent.io/blog/apache-kafka-to-amazon-s3-exactly-once/
https://github.com/karande/kafka-producer-file

https://github.com/linkedin/brooklin/issues/674

----------------------
https://stackoverflow.com/questions/19158118/implementing-large-file-uploads-via-http
apache commons fileupload
https://stackoverflow.com/questions/15432024/how-to-upload-a-file-using-commons-file-upload-streaming-api

https://blog.daftcode.pl/how-to-make-uploading-10x-faster-f5b3f9cfcd52

https://www.callicoder.com/configuring-spring-boot-application/

https://www.baeldung.com/spring-resttemplate-compressing-requests
