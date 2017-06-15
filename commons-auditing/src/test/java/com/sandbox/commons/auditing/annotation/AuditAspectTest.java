package com.sandbox.commons.auditing.annotation;

import com.sandbox.commons.auditing.config.AuditLogServiceConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @since 6/14/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {AuditLogServiceConfig.class})
public class AuditAspectTest {

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

//    @Resource
//    private AuditAspect auditAspect;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void foobar() {
        try {
            Mockito.when(proceedingJoinPoint.proceed()).thenReturn("Foobar");
            foobarMethod();
            Assert.assertTrue(true);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Audit(value = "foobar")
    public boolean foobarMethod(){
        return true;
    }
}
