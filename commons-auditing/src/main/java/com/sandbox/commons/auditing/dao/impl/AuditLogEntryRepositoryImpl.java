package com.sandbox.commons.auditing.dao.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.sandbox.commons.auditing.dao.AuditLogEntryRepository;
import com.sandbox.commons.auditing.domain.AuditEntry;

import javax.annotation.Resource;

/**
 * @since 6/9/17.
 */
public class AuditLogEntryRepositoryImpl implements AuditLogEntryRepository {

    @Resource
    private DynamoDBMapper dynamoDBMapper;

    public void setDynamoDBMapper(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public void save(AuditEntry auditEntry) {
        dynamoDBMapper.save(auditEntry);
    }

}
