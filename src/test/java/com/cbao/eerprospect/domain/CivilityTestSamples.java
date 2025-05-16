package com.cbao.eerprospect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CivilityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Civility getCivilitySample1() {
        return new Civility().id(1L).code("code1").label("label1");
    }

    public static Civility getCivilitySample2() {
        return new Civility().id(2L).code("code2").label("label2");
    }

    public static Civility getCivilityRandomSampleGenerator() {
        return new Civility().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
