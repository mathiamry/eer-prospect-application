package com.cbao.eerprospect.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProspectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prospect getProspectSample1() {
        return new Prospect()
            .id(1L)
            .lastName("lastName1")
            .firstName("firstName1")
            .dateOfBirth("dateOfBirth1")
            .cityOfBirth("cityOfBirth1")
            .countryOfBirth("countryOfBirth1")
            .nationality("nationality1")
            .motherLastName("motherLastName1")
            .motherFirstName("motherFirstName1")
            .wifeLastName("wifeLastName1")
            .wifeFirstName("wifeFirstName1")
            .familyStatusLabel("familyStatusLabel1")
            .countryOfResidence("countryOfResidence1")
            .city("city1")
            .addressLine("addressLine1")
            .phoneNumber("phoneNumber1")
            .email("email1")
            .idPaperType("idPaperType1")
            .idPaperNumber("idPaperNumber1")
            .idPaperDeliveryDate("idPaperDeliveryDate1")
            .idPaperDeliveryPlace("idPaperDeliveryPlace1")
            .idPaperValidityDate("idPaperValidityDate1")
            .professionCategory("professionCategory1")
            .profession("profession1")
            .employer("employer1");
    }

    public static Prospect getProspectSample2() {
        return new Prospect()
            .id(2L)
            .lastName("lastName2")
            .firstName("firstName2")
            .dateOfBirth("dateOfBirth2")
            .cityOfBirth("cityOfBirth2")
            .countryOfBirth("countryOfBirth2")
            .nationality("nationality2")
            .motherLastName("motherLastName2")
            .motherFirstName("motherFirstName2")
            .wifeLastName("wifeLastName2")
            .wifeFirstName("wifeFirstName2")
            .familyStatusLabel("familyStatusLabel2")
            .countryOfResidence("countryOfResidence2")
            .city("city2")
            .addressLine("addressLine2")
            .phoneNumber("phoneNumber2")
            .email("email2")
            .idPaperType("idPaperType2")
            .idPaperNumber("idPaperNumber2")
            .idPaperDeliveryDate("idPaperDeliveryDate2")
            .idPaperDeliveryPlace("idPaperDeliveryPlace2")
            .idPaperValidityDate("idPaperValidityDate2")
            .professionCategory("professionCategory2")
            .profession("profession2")
            .employer("employer2");
    }

    public static Prospect getProspectRandomSampleGenerator() {
        return new Prospect()
            .id(longCount.incrementAndGet())
            .lastName(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .dateOfBirth(UUID.randomUUID().toString())
            .cityOfBirth(UUID.randomUUID().toString())
            .countryOfBirth(UUID.randomUUID().toString())
            .nationality(UUID.randomUUID().toString())
            .motherLastName(UUID.randomUUID().toString())
            .motherFirstName(UUID.randomUUID().toString())
            .wifeLastName(UUID.randomUUID().toString())
            .wifeFirstName(UUID.randomUUID().toString())
            .familyStatusLabel(UUID.randomUUID().toString())
            .countryOfResidence(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .addressLine(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .idPaperType(UUID.randomUUID().toString())
            .idPaperNumber(UUID.randomUUID().toString())
            .idPaperDeliveryDate(UUID.randomUUID().toString())
            .idPaperDeliveryPlace(UUID.randomUUID().toString())
            .idPaperValidityDate(UUID.randomUUID().toString())
            .professionCategory(UUID.randomUUID().toString())
            .profession(UUID.randomUUID().toString())
            .employer(UUID.randomUUID().toString());
    }
}
