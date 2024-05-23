package com.nvelich.newsnba.logs;

import com.nvelich.newsnba.counter.RequestCounter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private RequestCounter requestCounter;

    @Before("execution(* com.nvelich.newsnba.service.*.*(..))")
    public void logBeforeMethodExecution() {
        int requestNumber = requestCounter.incrementAndGet();
        logger.info("Выполнение метода. Номер обращения: {}", requestNumber);
    }

    @AfterThrowing(pointcut = "execution(* com.nvelich.newsnba.service.*.*(..))", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        if (ex != null) {
            int requestNumber = requestCounter.getCurrentCount();
            logger.info(String.format("Ошибка в методе. Номер обращения: %d. Сообщение об ошибке: %s", requestNumber, ex.getMessage()));
        }
    }
}

