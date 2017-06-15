package com.sandbox.commons.auditing.annotation;

import java.lang.annotation.*;

/**
 * @since 6/12/17.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Audit {
    String value() default "";

    boolean isRoot() default false;

}
