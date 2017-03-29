package org.vaccineimpact.api.tests.extensions

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.app.extensions.getOther
import org.vaccineimpact.api.tests.MontaguTests

class GeneralExtensionsTests : MontaguTests()
{
    @Test
    fun `getOther returns the other object`()
    {
        assertThat(1.getOther(1, 2)).isEqualTo((2))
        assertThat(2.getOther(1, 2)).isEqualTo((1))
    }

    @Test
    fun `getOther throws exception if no arguments match the caller`()
    {
        assertThatThrownBy { 1.getOther(2, 3) }
    }
}