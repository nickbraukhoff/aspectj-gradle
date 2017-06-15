package com.sandbox.commons.auditing.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @since 6/12/17.
 */
public class DynamoDbResourceUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void createTables(Resource definitionResource, AmazonDynamoDB amazonDynamoDB, String tablePrefix) throws Exception {
        CreateTableRequest table = parseDefinition(definitionResource, tablePrefix);
        amazonDynamoDB.createTable(table);
//        createTable(table, tablePrefix);
    }

    public static CreateTableRequest parseDefinition(Resource definitionResource, String tablePrefix) throws Exception {
        Map tableDef = mapper.readValue(definitionResource.getFile(), Map.class);

        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tablePrefix + tableDef.get("TableName"))
                .withKeySchema(getKeySchemaElements((List<Map<String, String>>) tableDef.get("KeySchema")))
                .withAttributeDefinitions(getAttributeDefinitions((List<Map<String, String>>) tableDef.get("AttributeDefinitions")))
                .withProvisionedThroughput(getProvisionedThroughput((Map<String, Object>) tableDef.get("ProvisionedThroughput")))
                .withGlobalSecondaryIndexes(getGlobalSecondaryIndexes((List<Map<String, Object>>) tableDef.get("GlobalSecondaryIndexes")))
                .withLocalSecondaryIndexes(getLocalSecondaryIndexes((List<Map<String, Object>>) tableDef.get("LocalSecondaryIndexes")));
//                    .withStreamSpecification(description.getStreamSpecification());
        return request;
    }


    public static List<KeySchemaElement> getKeySchemaElements(final List<Map<String, String>> keys) {
        final List<KeySchemaElement> elements = new ArrayList<>();
        keys.forEach(item -> {
            final KeySchemaElement element = new KeySchemaElement();
            element.setAttributeName(item.get("AttributeName"));
            element.setKeyType(item.get("KeyType"));
            elements.add(element);
        });
        return elements;
    }

    public static List<AttributeDefinition> getAttributeDefinitions(final List<Map<String, String>> maps) {
        final List<AttributeDefinition> elements = new ArrayList<>();
        maps.forEach(item -> {
            final AttributeDefinition element = new AttributeDefinition();
            element.setAttributeName(item.get("AttributeName"));
            element.setAttributeType(item.get("AttributeType"));
            elements.add(element);
        });
        return elements;
    }

    public static List<LocalSecondaryIndex> getLocalSecondaryIndexes(final List<Map<String, Object>> maps) {
        final List<LocalSecondaryIndex> elements = new ArrayList<>();
        maps.forEach(item -> {
            final LocalSecondaryIndex element = new LocalSecondaryIndex();
            element.setIndexName(String.valueOf(item.get("IndexName")));

            Map<String, Object> projections = (Map<String, Object>) item.get("Projection");
            element.setProjection(new Projection()
                    .withProjectionType(String.valueOf(projections.get("ProjectionType")))
                    .withNonKeyAttributes((Collection<String>) projections.get("NonKeyAttributes")));
            element.setKeySchema(getKeySchemaElements((List<Map<String, String>>) item.get("KeySchema")));
            elements.add(element);
        });
        return elements;
    }

    public static List<GlobalSecondaryIndex> getGlobalSecondaryIndexes(final List<Map<String, Object>> maps) {
        final List<GlobalSecondaryIndex> elements = new ArrayList<>();
        maps.forEach(item -> {
            final GlobalSecondaryIndex element = new GlobalSecondaryIndex();
            element.setIndexName(String.valueOf(item.get("IndexName")));

            Map<String, Object> projections = (Map<String, Object>) item.get("Projection");
            element.setProjection(new Projection()
                    .withProjectionType(String.valueOf(projections.get("ProjectionType")))
                    .withNonKeyAttributes((Collection<String>) projections.get("NonKeyAttributes")));
            element.setKeySchema(getKeySchemaElements((List<Map<String, String>>) item.get("KeySchema")));
            element.setProvisionedThroughput(getProvisionedThroughput((Map<String, Object>) item.get("ProvisionedThroughput")));
            elements.add(element);
        });
        return elements;
    }

    public static ProvisionedThroughput getProvisionedThroughput(final Map<String, Object> maps) {
        return new ProvisionedThroughput(new Long(String.valueOf(maps.get("ReadCapacityUnits"))), new Long(String.valueOf(maps.get("WriteCapacityUnits"))));
    }
}

