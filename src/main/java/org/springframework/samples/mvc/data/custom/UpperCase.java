package org.springframework.samples.mvc.data.custom;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UpperCase {
    String value();
}