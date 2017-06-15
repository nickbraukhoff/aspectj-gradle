package com.sandbox.commons.auditing.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;

/**
 * @since 6/9/17.
 */
@DynamoDBTable(tableName = "AuditEntry")
public class AuditEntry {
    private String healthSystem;
    private String timeStampId; // is this set when it is created or when it is written to the database?
    private String patientId;
    private String payer;
    private String memberId;
    private String userId;
    private String service;
    private String correlationId;
    private List<String> events;
    private String sessionId;

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName="HealthSystem")
    public String getHealthSystem() {
        return healthSystem;
    }

    public void setHealthSystem(String healthSystem) {
        this.healthSystem = healthSystem;
    }

    @DynamoDBRangeKey
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "UserId_TimeStampId_Index")
    @DynamoDBAttribute(attributeName="TimeStampId")
    public String getTimeStampId() {
        return timeStampId;
    }

    public void setTimeStampId(String timeStampId) {
        this.timeStampId = timeStampId;
    }

    @DynamoDBIndexRangeKey(localSecondaryIndexName = "HealthSystem_PatientId_Index")
    @DynamoDBAttribute(attributeName="PatientId")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "Payer_MemberId_Index")
    @DynamoDBAttribute(attributeName="Payer")
    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "Payer_MemberId_Index")
    @DynamoDBAttribute(attributeName="MemberId")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "UserId_TimeStampId_Index")
    @DynamoDBAttribute(attributeName="UserId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName="Service")
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @DynamoDBAttribute(attributeName="CorrelationId")
    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @DynamoDBAttribute(attributeName="Events")
    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    @DynamoDBAttribute(attributeName="SessionId")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
