package org.vaccineimpact.api.security.tests

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import org.vaccineimpact.api.db.Tables.ROLE
import org.vaccineimpact.api.db.Tables.USER_GROUP_ROLE
import org.vaccineimpact.api.db.direct.addUserForTesting
import org.vaccineimpact.api.security.addAllGlobalRoles
import org.vaccineimpact.api.test_helpers.DatabaseTest

class AddRolesToUserTests : DatabaseTest()
{
    @Test
    fun `can give all global roles to user`()
    {
        val args = listOf("test.user")

        withDatabase {
            it.addUserForTesting("test.user")
        }

        addAllGlobalRoles(args)

        withDatabase {
            val userRoles = it.dsl.select(USER_GROUP_ROLE.ROLE)
                    .from(USER_GROUP_ROLE)
                    .where(USER_GROUP_ROLE.USER_GROUP.eq("test.user"))
                    .fetch()

            val globalRoles = it.dsl.select(ROLE.ID)
                    .from(ROLE)
                    .where(ROLE.SCOPE_PREFIX.isNull)
                    .fetch()

            assertThat(userRoles).hasSameElementsAs(globalRoles)
        }

    }
}