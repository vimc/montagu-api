package org.vaccineimpact.api.tests.models

import org.assertj.core.api.Assertions
import org.junit.Test
import org.vaccineimpact.api.models.CreateBurdenEstimateSet
import org.vaccineimpact.api.serialization.validation.ValidationException

class CreateBurdenEstimateSetTests : ValidationTests()
{
    @Test
    fun `empty json throws validation exception`()
    {
        val badModelJson = "{}"
        Assertions.assertThatThrownBy {
            binder.deserialize<CreateBurdenEstimateSet>(badModelJson, CreateBurdenEstimateSet::class.java)
        }.matches {
            val errors = (it as ValidationException).errors
            Assertions.assertThat(errors).hasSize(1)
            Assertions.assertThat(errors[0].code).isEqualTo("invalid-field:type:missing")
            true
        }
    }

    @Test
    fun `missing type throws validation exception`()
    {
        val badModelJson = "{ \"modelRunParameterSet\" : \"1\"}"
        Assertions.assertThatThrownBy {
            binder.deserialize<CreateBurdenEstimateSet>(badModelJson, CreateBurdenEstimateSet::class.java)
        }.matches {
            val errors = (it as ValidationException).errors
            Assertions.assertThat(errors).hasSize(1)
            Assertions.assertThat(errors[0].code).isEqualTo("invalid-field:type:missing")
            true
        }
    }

    @Test
    fun `missing inner type throws validation exception`()
    {
        val badModelJson = "{\"type\":{ \"details\": \"whatever\" }, \"modelRunParameterSet\" : \"1\"}"
        Assertions.assertThatThrownBy {
            binder.deserialize<CreateBurdenEstimateSet>(badModelJson, CreateBurdenEstimateSet::class.java)
        }.matches {
            val errors = (it as ValidationException).errors
            Assertions.assertThat(errors.count()).isEqualTo(1)
            Assertions.assertThat(errors[0].code).isEqualTo("invalid-field:type:type:missing")
            true
        }
    }

    @Test
    fun `missing model run parameter set does not throw validation exception when type is central`()
    {
        val badModelJson = "{\"type\":{ \"type\": \"central-averaged\"}}"
        binder.deserialize<CreateBurdenEstimateSet>(badModelJson, CreateBurdenEstimateSet::class.java)
    }

    @Test
    fun `missing model run parameter set throws validation exception when type is stochastic`()
    {
        val badModelJson = "{\"type\":{ \"type\": \"stochastic\"}}"

        Assertions.assertThatThrownBy {
            binder.deserialize<CreateBurdenEstimateSet>(badModelJson, CreateBurdenEstimateSet::class.java)
        }.matches {
            val errors = (it as ValidationException).errors
            Assertions.assertThat(errors.count()).isEqualTo(1)
            Assertions.assertThat(errors[0].code).isEqualTo("invalid-field:model_run_parameter_set:missing")
            true
        }
    }

}