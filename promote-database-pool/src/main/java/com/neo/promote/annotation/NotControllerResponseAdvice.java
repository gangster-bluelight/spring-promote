package com.neo.promote.annotation;

import java.lang.annotation.*;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotControllerResponseAdvice {
}
