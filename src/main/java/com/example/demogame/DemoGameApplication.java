package com.example.demogame;

import com.example.demogame.model.*;
import com.example.demogame.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ContextResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.function.ServerResponse;

import javax.naming.Context;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class DemoGameApplication {

    static Logger logger = LoggerFactory.getLogger(DemoGameApplication.class);

    public static JsonNode getPlayFile(String[] args, InputStream defaultFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        if (args.length > 0){
            logger.info("getting the user file: {}", args[0]);
            //get file name from command line
            String fileName = args[0];
            try{
                File file = new File(fileName);
                jsonNode = objectMapper.readTree(file);
            }
            catch (IOException e){
                logger.error("file not found");
                logger.warn("getting the default file");
                jsonNode = objectMapper.readTree(defaultFile);
            }
        }else{
            logger.info("getting the default file");
            jsonNode = objectMapper.readTree(defaultFile);
        }

        return jsonNode;
    }

    public static void main(String[] args) throws IOException {
         ConfigurableApplicationContext cx = SpringApplication.run(DemoGameApplication.class, args);

         Resource resource = cx.getResource("classpath:static/defaultGame.json");
         InputStream defaultFile = resource.getInputStream();

         GameService gameService = cx.getBean(GameService.class);
         JsonNode jsonNode = getPlayFile(args, defaultFile);
         gameService.playGame(jsonNode);
    }
}
