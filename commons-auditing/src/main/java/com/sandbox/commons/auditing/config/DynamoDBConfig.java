package com.sandbox.commons.auditing.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @since 6/14/17.
 */
@Configuration
public class DynamoDBConfig {

    @Value("${dynamoDB.url}")
    private String url;

    @Value("${dynamoDB.region}")
    private String region;

    @Value("${dynamoDB.environment}")
    private String environment;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        // Use DefaultAWSCredentialsProviderChain to enable use of environmental credentials
        final AmazonDynamoDBClientBuilder dbClientBuilder = AmazonDynamoDBClientBuilder.standard();

        if (StringUtils.hasText(region)) {
            dbClientBuilder.setRegion(region);
        }

        return dbClientBuilder.build();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        final DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        builder.setTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(environment));
        // Discard legacy behavior, support sets
        builder.setConversionSchema(ConversionSchemas.V2);
        return builder.build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB(), dynamoDBMapperConfig());
    }
}
