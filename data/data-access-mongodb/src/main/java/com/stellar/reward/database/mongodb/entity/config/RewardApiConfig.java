package com.stellar.reward.database.mongodb.entity.config;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;


@Configuration
@EnableAsync
public class RewardApiConfig {

    Logger logger = LoggerFactory.getLogger(RewardApiConfig.class);

    @Value("${dbUserName}")
    private String dbusername;


    @Value("${dbpassword}")
    private String dbpassword;

    @Value("${database}")
    private String database;


    @Value("${dpIp}")
    private String dpIp;


    @Value("${dpPort}")
    private String dpPort;

    @Value("${numberOfSummary}")
    private int numberOfSummary;


    public String getDpIp() {
        return dpIp;
    }

    public void setDpIp(String dpIp) {
        this.dpIp = dpIp;
    }

    public String getDpPort() {
        return dpPort;
    }

    public void setDpPort(String dpPort) {
        this.dpPort = dpPort;
    }


    public String getDbusername() {
        return dbusername;
    }

    public void setDbusername(String dbusername) {
        this.dbusername = dbusername;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }


    public int getNumberOfSummary() {
        return numberOfSummary;
    }

    public void setNumberOfSummary(int numberOfSummary) {
        this.numberOfSummary = numberOfSummary;
    }


    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = null;
        try {
            String mongoUserName = getDbusername();
            String mongoPassword = getDbpassword();
            ServerAddress serverAddress = new ServerAddress(getDpIp(), Integer.parseInt(getDpPort()));
            List<MongoCredential> credentialsList = new ArrayList<>();
            MongoCredential mongoCredential = MongoCredential.createCredential(mongoUserName,getDatabase(), mongoPassword.toCharArray());
            credentialsList.add(mongoCredential);
            MongoClient mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(serverAddress)))
                            .credential(mongoCredential).build());
            mongoTemplate = new MongoTemplate(mongoClient , getDatabase());
            ((MappingMongoConverter)mongoTemplate.getConverter()).setTypeMapper(new DefaultMongoTypeMapper(null));
        } catch (Exception e) {
            logger.info("error occurred while initate mongo connection"+e);
        }
        return mongoTemplate;
    }


}