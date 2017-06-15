package com.sandbox.commons.auditing.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @since 6/12/17.
 */
@Aspect
public class AuditAspect {

    @Around(value = "execution(* *(..)) && @annotation(audit)")
    public Object captureAuditData(final ProceedingJoinPoint joinPoint, final Audit audit) throws Throwable {
        Object value = null;
        try {
            System.out.println("Before advice");
            value = joinPoint.proceed();
            System.out.println("After advice");
        } catch (Throwable t) {
            System.out.println("After Throwing ");
        }
        return value;

    }
}
