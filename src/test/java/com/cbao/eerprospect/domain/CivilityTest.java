package com.cbao.eerprospect.domain;

import static com.cbao.eerprospect.domain.CivilityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbao.eerprospect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CivilityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Civility.class);
        Civility civility1 = getCivilitySample1();
        Civility civility2 = new Civility();
        assertThat(civility1).isNotEqualTo(civility2);

        civility2.setId(civility1.getId());
        assertThat(civility1).isEqualTo(civility2);

        civility2 = getCivilitySample2();
        assertThat(civility1).isNotEqualTo(civility2);
    }
}
