package com.cbao.eerprospect.domain;

import static com.cbao.eerprospect.domain.IncomePeriodicityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbao.eerprospect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IncomePeriodicityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomePeriodicity.class);
        IncomePeriodicity incomePeriodicity1 = getIncomePeriodicitySample1();
        IncomePeriodicity incomePeriodicity2 = new IncomePeriodicity();
        assertThat(incomePeriodicity1).isNotEqualTo(incomePeriodicity2);

        incomePeriodicity2.setId(incomePeriodicity1.getId());
        assertThat(incomePeriodicity1).isEqualTo(incomePeriodicity2);

        incomePeriodicity2 = getIncomePeriodicitySample2();
        assertThat(incomePeriodicity1).isNotEqualTo(incomePeriodicity2);
    }
}
