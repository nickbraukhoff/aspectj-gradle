package com.sandbox.commons.auditing.service.impl;

import com.sandbox.commons.auditing.dao.AuditLogEntryRepository;
import com.sandbox.commons.auditing.domain.AuditEntry;
import com.sandbox.commons.auditing.service.AuditLogService;

import javax.annotation.Resource;

/**
 * @since 6/12/17.
 */
public class AuditLogServiceImpl implements AuditLogService {

    @Resource
    private AuditLogEntryRepository auditLogEntryRepository;

    @Override
    public void log(final AuditEntry auditEntry) {
        auditLogEntryRepository.save(auditEntry);
    }

}
