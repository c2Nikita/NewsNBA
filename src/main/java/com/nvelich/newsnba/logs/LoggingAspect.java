package com.nvelich.newsnba.logs;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.nvelich.newsnba.service.*.*(..))")
    public void logBeforeMethodExecution() {
        logger.info("Выполнение метода");
    }

    @AfterThrowing(pointcut = "execution(* com.nvelich.newsnba.service.*.*(..))", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        logger.error("Ошибка в методе: " + ex.getMessage());
    }
}
