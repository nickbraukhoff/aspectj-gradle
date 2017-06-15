package com.sandbox.commons.auditing.domain;

/**
 * @since 6/14/17.
 */
public final class AuditTransactionManager {

    private static final ThreadLocal<AuditEntry> AUDIT_CONTEXT = new ThreadLocal<>();

    public static void setAuditContext(final AuditEntry auditContext) {
        AUDIT_CONTEXT.set(auditContext);
    }

    public static AuditEntry getAuditContext() {
        return AUDIT_CONTEXT.get();
    }

    public static void removeUserContext() {
        AUDIT_CONTEXT.remove();
    }
}
