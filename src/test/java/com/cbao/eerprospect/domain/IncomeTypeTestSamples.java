package com.cbao.eerprospect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IncomeTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IncomeType getIncomeTypeSample1() {
        return new IncomeType().id(1L).code("code1").label("label1");
    }

    public static IncomeType getIncomeTypeSample2() {
        return new IncomeType().id(2L).code("code2").label("label2");
    }

    public static IncomeType getIncomeTypeRandomSampleGenerator() {
        return new IncomeType().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
