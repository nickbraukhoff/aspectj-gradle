package com.sandbox.commons.auditing.service;

import com.sandbox.commons.auditing.domain.AuditEntry;

/**
 * @since 6/9/17.
 */
public interface AuditLogService {
    void log(AuditEntry auditEntry);
}
