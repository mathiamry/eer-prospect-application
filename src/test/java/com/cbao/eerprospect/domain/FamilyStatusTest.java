package com.cbao.eerprospect.domain;

import static com.cbao.eerprospect.domain.FamilyStatusTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbao.eerprospect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FamilyStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyStatus.class);
        FamilyStatus familyStatus1 = getFamilyStatusSample1();
        FamilyStatus familyStatus2 = new FamilyStatus();
        assertThat(familyStatus1).isNotEqualTo(familyStatus2);

        familyStatus2.setId(familyStatus1.getId());
        assertThat(familyStatus1).isEqualTo(familyStatus2);

        familyStatus2 = getFamilyStatusSample2();
        assertThat(familyStatus1).isNotEqualTo(familyStatus2);
    }
}
