package org.vaccineimpact.api.security.tests

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.Tables.*
import org.vaccineimpact.api.db.direct.addGroup
import org.vaccineimpact.api.db.direct.addUserForTesting
import org.vaccineimpact.api.db.fromJoinPath
import org.vaccineimpact.api.security.AddUserToGroupOptions
import org.vaccineimpact.api.security.Groups
import org.vaccineimpact.api.test_helpers.DatabaseTest

class AddUserToGroupTests : DatabaseTest()
{
    @Test
    fun `can give groups to user`()
    {
        val options = AddUserToGroupOptions("username", Groups.GroupList(listOf("group1", "group2")))
        JooqContext().use { db ->
            db.addUserForTesting("username")
            db.addGroup("group1")
            db.addGroup("group2")
            options.run()
            val groups = db.dsl.select(USER_GROUP_ROLE.SCOPE_ID)
                    .fromJoinPath(APP_USER, USER_GROUP_MEMBERSHIP, USER_GROUP, USER_GROUP_ROLE, ROLE)
                    .where(APP_USER.USERNAME.eq("username"))
                    .and(ROLE.NAME.eq("member"))
                    .and(ROLE.SCOPE_PREFIX.eq("modelling-group"))
                    .fetch()
                    .map { it[USER_GROUP_ROLE.SCOPE_ID] }
                    .toSet()
            assertThat(groups).isEqualTo(setOf("group1", "group2"))
        }
    }
}