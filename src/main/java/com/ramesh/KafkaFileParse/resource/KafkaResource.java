package com.ramesh.KafkaFileParse.resource;

import com.ramesh.KafkaFileParse.config.KafkaConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@RestController
@RequestMapping("kafka")
class kafkaResource {
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    //private Consumer<String, byte[]> kafkaConsumer;
    private static final String TOPIC = "document";

    @RequestMapping(value = "/publish",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String postData(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedReader br = null;

        String fileName = null;
        String srcDir = "ProducerData/";


        KafkaConfig producerCreator = new KafkaConfig("document", false);
        try {
            fileName = file.getOriginalFilename();
            System.out.println("File name=" + fileName);

            File newFile = new File(srcDir);
            newFile = new File(srcDir + fileName);
            newFile.createNewFile();
            System.out.println("File created in : "+srcDir+fileName);
            try (FileOutputStream fos = new FileOutputStream(newFile)) {
                fos.write(file.getBytes());
                if(fos!=null){
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Step 2");
            File localFile = new File(srcDir,fileName);
            System.out.println("File="+localFile);
           // File listOfFiles = localFile+fileName;
           // localFile = new File(srcDir+fileName);
            FileInputStream fileInputStream = null;
//            System.out.println("Length="+listOfFiles.length);
//            if (listOfFiles.length > 0) {
              //  for (int i = 0; listOfFiles.length > i; i++) {
                    if (localFile.isFile()) {
                        try {
                            byte[] byteFile = new byte[(int) localFile.length()];
                            System.out.println("Byte file 1st="+byteFile);
                            fileInputStream = new FileInputStream(localFile);
                            System.out.println("fileinputstream="+fileInputStream);
                            fileInputStream.read(byteFile);
                            System.out.println("Byte file 2nd="+byteFile);
                            System.out.println("File name="+localFile.getName());
                            System.out.println("Before send");

                            /*
                            byte[] byteFile = Files.readAllBytes(localFile.toPath());
                            fileInputStream = new FileInputStream(localFile);
                            fileInputStream.read(byteFile);
                            */
                            producerCreator.sendMessage(localFile.getName(), byteFile);
                            System.out.println("Posted to KAFKA");
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
              //  }
          //  }


            System.out.println("Deleting temp file on path " + newFile.getAbsolutePath());
            try {
                Files.deleteIfExists(Paths.get(String.valueOf(newFile.getAbsoluteFile())));
            } catch (NoSuchFileException e) {
                System.out.println("No such file/directory exists");
            } catch (DirectoryNotEmptyException e) {
                System.out.println("Directory is not empty.");
            } catch (IOException e) {
                System.out.println("Invalid permissions.");
            }
            System.out.println("File deletion completed");



        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
        return "Published successfully";
    }
}

