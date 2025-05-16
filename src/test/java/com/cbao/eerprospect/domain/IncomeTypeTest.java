package com.cbao.eerprospect.domain;

import static com.cbao.eerprospect.domain.IncomeTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbao.eerprospect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IncomeTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeType.class);
        IncomeType incomeType1 = getIncomeTypeSample1();
        IncomeType incomeType2 = new IncomeType();
        assertThat(incomeType1).isNotEqualTo(incomeType2);

        incomeType2.setId(incomeType1.getId());
        assertThat(incomeType1).isEqualTo(incomeType2);

        incomeType2 = getIncomeTypeSample2();
        assertThat(incomeType1).isNotEqualTo(incomeType2);
    }
}
