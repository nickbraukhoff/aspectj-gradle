package com.sandbox.commons.auditing.config;

import com.sandbox.commons.auditing.annotation.AuditAspect;
import com.sandbox.commons.auditing.dao.AuditLogEntryRepository;
import com.sandbox.commons.auditing.dao.impl.AuditLogEntryRepositoryImpl;
import com.sandbox.commons.auditing.service.AuditLogService;
import com.sandbox.commons.auditing.service.impl.AuditLogServiceImpl;
import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.*;

/**
 * @since 6/14/17.
 */
@Configuration
@Import({LocalDynamoDbConfig.class})
public class AuditLogServiceConfig {

    @Bean
    public AuditAspect auditAspect() {
        AuditAspect auditAspect = Aspects.aspectOf(AuditAspect.class);
        return auditAspect;
    }

    @Bean
    public AuditLogService auditLogService() {
        return new AuditLogServiceImpl();
    }

    @Bean
    public AuditLogEntryRepository auditLogEntryRepository() {
        return new AuditLogEntryRepositoryImpl();
    }
}
