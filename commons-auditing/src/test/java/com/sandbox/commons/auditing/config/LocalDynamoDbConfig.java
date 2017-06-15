package com.sandbox.commons.auditing.config;

import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.sandbox.commons.auditing.util.DynamoDbResourceUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @since 6/12/17.
 */
@Configuration
public class LocalDynamoDbConfig {
    public static final String ENVIRONMENT = "LOCAL";
    private static final String TABLE_PREFIX = ENVIRONMENT + '_';


    @Bean(destroyMethod = "shutdown")
    public AmazonDynamoDBLocal embeddedAmazonDynamoDB() throws Exception {

        AmazonDynamoDBLocal amazonDynamoDBLocal = DynamoDBEmbedded.create();
        DynamoDbResourceUtil.createTables(new ClassPathResource("/definition.json"), amazonDynamoDBLocal, TABLE_PREFIX);

        return amazonDynamoDBLocal;
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        final DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        builder.setTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(TABLE_PREFIX));
        // Discard legacy behavior, support sets
        builder.setConversionSchema(ConversionSchemas.V2);
        return builder.build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() throws Exception {
        return new DynamoDBMapper(embeddedAmazonDynamoDB(), dynamoDBMapperConfig());
    }

}
