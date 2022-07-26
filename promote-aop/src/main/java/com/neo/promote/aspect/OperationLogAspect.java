package com.neo.promote.aspect;

import com.neo.promote.annotation.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Slf4j
@Aspect
@Component
public class OperationLogAspect {
    @Pointcut("@annotation(operationLog)")
    private void point(OperationLog operationLog) {
    }

    @Before(value = "point(operationLog)", argNames = "operationLog")
    public void doBefore(OperationLog operationLog) {
        log.info("operation log before...");
    }

    @Around(value = "point(operationLog)", argNames = "joinPoint,operationLog")
    public Object doAround(ProceedingJoinPoint joinPoint, OperationLog operationLog) {
        try {
            log.info("operation log around before...");
            Object proceed = joinPoint.proceed();
            log.info("activity return value:{}", proceed);
            log.info("operation log around after...");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "aspect success";
    }

    @After(value = "point(operationLog)", argNames = "operationLog")
    public void doAfter(OperationLog operationLog) {
        log.info("operation log after...");
    }

    @AfterReturning(pointcut = "point(operationLog)", argNames = "operationLog")
    public void doAfterReturning(OperationLog operationLog) {
        log.info("operation log after return...");
    }

    @AfterThrowing(pointcut = "point(operationLog)", argNames = "operationLog")
    public void doAfterThrowing(OperationLog operationLog) {
        log.info("operation log after throwing...");
    }
}
