package com.nvelich.newsnba.counter;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RequestCounter {
    private final AtomicInteger counter = new AtomicInteger(0);

    public synchronized int incrementAndGet() {
        return counter.incrementAndGet();
    }

    public synchronized int getCurrentCount() {
        return counter.get();
    }
}