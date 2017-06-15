package com.sandbox.commons.auditing.dto;

/**
 * @since 6/14/17.
 */
public class AuditEntryRequest {

    private String rangeKey;
    private String hashKey;

    public AuditEntryRequest withRangeKey(final String rangeKey){
        this.rangeKey = rangeKey;
        return this;
    }

    public AuditEntryRequest withHashKey(final String hashKey){
        this.hashKey = hashKey;
        return this;
    }

    public String getRangeKey() {
        return rangeKey;
    }

    public String getHashKey() {
        return hashKey;
    }
}
