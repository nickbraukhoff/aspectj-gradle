package com.sandbox.commons.auditing.dao;

import com.sandbox.commons.auditing.domain.AuditEntry;

/**
 * @since 6/9/17.
 */
public interface AuditLogEntryRepository {
    void save(AuditEntry auditEntry);
}
