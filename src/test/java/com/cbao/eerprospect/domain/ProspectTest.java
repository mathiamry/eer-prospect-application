package com.cbao.eerprospect.domain;

import static com.cbao.eerprospect.domain.CivilityTestSamples.*;
import static com.cbao.eerprospect.domain.FamilyStatusTestSamples.*;
import static com.cbao.eerprospect.domain.IncomePeriodicityTestSamples.*;
import static com.cbao.eerprospect.domain.IncomeTypeTestSamples.*;
import static com.cbao.eerprospect.domain.ProspectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbao.eerprospect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProspectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prospect.class);
        Prospect prospect1 = getProspectSample1();
        Prospect prospect2 = new Prospect();
        assertThat(prospect1).isNotEqualTo(prospect2);

        prospect2.setId(prospect1.getId());
        assertThat(prospect1).isEqualTo(prospect2);

        prospect2 = getProspectSample2();
        assertThat(prospect1).isNotEqualTo(prospect2);
    }

    @Test
    void civilityTest() {
        Prospect prospect = getProspectRandomSampleGenerator();
        Civility civilityBack = getCivilityRandomSampleGenerator();

        prospect.setCivility(civilityBack);
        assertThat(prospect.getCivility()).isEqualTo(civilityBack);

        prospect.civility(null);
        assertThat(prospect.getCivility()).isNull();
    }

    @Test
    void familyStatusTest() {
        Prospect prospect = getProspectRandomSampleGenerator();
        FamilyStatus familyStatusBack = getFamilyStatusRandomSampleGenerator();

        prospect.setFamilyStatus(familyStatusBack);
        assertThat(prospect.getFamilyStatus()).isEqualTo(familyStatusBack);

        prospect.familyStatus(null);
        assertThat(prospect.getFamilyStatus()).isNull();
    }

    @Test
    void incomeTypeTest() {
        Prospect prospect = getProspectRandomSampleGenerator();
        IncomeType incomeTypeBack = getIncomeTypeRandomSampleGenerator();

        prospect.setIncomeType(incomeTypeBack);
        assertThat(prospect.getIncomeType()).isEqualTo(incomeTypeBack);

        prospect.incomeType(null);
        assertThat(prospect.getIncomeType()).isNull();
    }

    @Test
    void incomePeriodicityTest() {
        Prospect prospect = getProspectRandomSampleGenerator();
        IncomePeriodicity incomePeriodicityBack = getIncomePeriodicityRandomSampleGenerator();

        prospect.setIncomePeriodicity(incomePeriodicityBack);
        assertThat(prospect.getIncomePeriodicity()).isEqualTo(incomePeriodicityBack);

        prospect.incomePeriodicity(null);
        assertThat(prospect.getIncomePeriodicity()).isNull();
    }
}
