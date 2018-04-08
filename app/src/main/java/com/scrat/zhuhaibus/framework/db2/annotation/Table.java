package com.scrat.zhuhaibus.framework.db2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {
    String tableName() default "";

    // @Table(uniques = {@Unique(columnNames = {"a", "b"}), @Unique(columnNames = {"c"})})
    Unique[] uniques() default {};

    int version() default 0;
}
