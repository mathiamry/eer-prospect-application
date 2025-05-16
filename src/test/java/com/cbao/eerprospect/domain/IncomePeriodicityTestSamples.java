package com.cbao.eerprospect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IncomePeriodicityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IncomePeriodicity getIncomePeriodicitySample1() {
        return new IncomePeriodicity().id(1L).code("code1").label("label1");
    }

    public static IncomePeriodicity getIncomePeriodicitySample2() {
        return new IncomePeriodicity().id(2L).code("code2").label("label2");
    }

    public static IncomePeriodicity getIncomePeriodicityRandomSampleGenerator() {
        return new IncomePeriodicity()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .label(UUID.randomUUID().toString());
    }
}
