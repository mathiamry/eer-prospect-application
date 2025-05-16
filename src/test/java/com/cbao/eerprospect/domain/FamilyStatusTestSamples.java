package com.cbao.eerprospect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FamilyStatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FamilyStatus getFamilyStatusSample1() {
        return new FamilyStatus().id(1L).code("code1").label("label1");
    }

    public static FamilyStatus getFamilyStatusSample2() {
        return new FamilyStatus().id(2L).code("code2").label("label2");
    }

    public static FamilyStatus getFamilyStatusRandomSampleGenerator() {
        return new FamilyStatus().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
