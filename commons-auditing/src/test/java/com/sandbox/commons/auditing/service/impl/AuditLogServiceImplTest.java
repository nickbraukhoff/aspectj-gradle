package com.sandbox.commons.auditing.service.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.common.collect.Lists;
import com.sandbox.commons.auditing.config.LocalDynamoDbConfig;
import com.sandbox.commons.auditing.domain.AuditEntry;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 6/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {LocalDynamoDbConfig.class})
public class AuditLogServiceImplTest {

    @Resource
    private AmazonDynamoDB amazonDynamoDB;

    @Resource
    private DynamoDBMapper dynamoDBMapper;

    @Test
    public void saveAndLoadItemUsingAmazonDynamoDB() {
        final ListTablesResult result = amazonDynamoDB.listTables();
        final Map<String, AttributeValue> attributeValues = new HashMap<>();

        attributeValues.put("HealthSystem", new AttributeValue("ATE_DEV_EPIC"));
        attributeValues.put("TimeStampId", new AttributeValue("2016-04-07T05:00Z_282396d6-506d-11e7-b114-b2f933d5fe66"));
        attributeValues.put("PatientId", new AttributeValue("23412341234"));
        attributeValues.put("Payer", new AttributeValue("ATE_DEV_EPIC"));
        attributeValues.put("MemberId", new AttributeValue("254122341234"));
        attributeValues.put("UserId", new AttributeValue("ATE_DEV_EPIC_foobar"));
        attributeValues.put("Service", new AttributeValue("ATE_DEV_EPIC"));
        attributeValues.put("CorrelationId", new AttributeValue("4471ccbf-1c6a-4869-bfb5-c4a0c02da9e5"));
        attributeValues.put("Events", new AttributeValue(Lists.newArrayList("ATE_DEV_EPIC")));
        attributeValues.put("SessionId", new AttributeValue("4471ccbf-1c6a-4869-bfb5-c4a0c02da9e5"));

        amazonDynamoDB.putItem(result.getTableNames().get(0), attributeValues);
        final Map<String, AttributeValue> attributeKey = new HashMap<>();

        attributeKey.put("HealthSystem", new AttributeValue("ATE_DEV_EPIC"));
        attributeKey.put("TimeStampId", new AttributeValue("2016-04-07T05:00Z_282396d6-506d-11e7-b114-b2f933d5fe66"));

        final ScanRequest scanRequest = new ScanRequest()
                .withTableName(result.getTableNames().get(0))
                .withLimit(1);

        final ScanResult scanResult = amazonDynamoDB.scan(scanRequest);

        final AuditEntry auditEntry = dynamoDBMapper.marshallIntoObject(AuditEntry.class, scanResult.getItems().get(0));


        Assert.assertNotNull(result);
    }

    @Test
    public void saveAndLoadItemUsingDynamoDBMapper() {

        final AuditEntry auditEntry = new AuditEntry();
        auditEntry.setHealthSystem("ATE_DEV_EPIC");
        auditEntry.setTimeStampId("2016-04-07T05:00Z_282396d6-506d-11e7-b114-b2f933d5fe62");
        auditEntry.setPatientId("23412341232");
        auditEntry.setPayer("ATE_DEV_EPIC");
        auditEntry.setMemberId("25412234122");
        auditEntry.setUserId("ATE_DEV_EPIC_foobar");
        auditEntry.setService("ATE_DEV_EPIC");
        auditEntry.setCorrelationId("4471ccbf-1c6a-4869-bfb5-c4a0c02da923");
        auditEntry.setEvents(Lists.newArrayList("ATE_DEV_EPIC"));
        auditEntry.setSessionId("4471ccbf-1c6a-4869-bfb5-c4a0c02da9e5");

        dynamoDBMapper.save(auditEntry);
        AuditEntry response = dynamoDBMapper.load(AuditEntry.class, "ATE_DEV_EPIC", "2016-04-07T05:00Z_282396d6-506d-11e7-b114-b2f933d5fe62");

        Assert.assertNotNull(response);
    }
}
