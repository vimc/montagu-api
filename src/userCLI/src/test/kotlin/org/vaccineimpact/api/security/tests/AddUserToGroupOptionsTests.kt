package org.vaccineimpact.api.security.tests

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.security.ActionException
import org.vaccineimpact.api.security.AddUserToGroupOptions
import org.vaccineimpact.api.security.Groups
import org.vaccineimpact.api.test_helpers.MontaguTests

class AddUserToGroupOptionsTests : MontaguTests()
{
    @Test
    fun `can parse one group`()
    {
        val options = AddUserToGroupOptions.parseArgs(listOf("username", "group"))
        assertThat(options).isEqualTo(AddUserToGroupOptions(
                "username",
                Groups.GroupList(listOf("group"))
        ))
    }

    @Test
    fun `can parse multiple groups`()
    {
        val options = AddUserToGroupOptions.parseArgs(listOf("username", "a", "b", "c"))
        assertThat(options).isEqualTo(AddUserToGroupOptions(
                "username",
                Groups.GroupList(listOf("a", "b", "c"))
        ))
    }

    @Test
    fun `can parse ALL group option`()
    {
        val options = AddUserToGroupOptions.parseArgs(listOf("username", "ALL"))
        assertThat(options).isEqualTo(AddUserToGroupOptions(
                "username",
                Groups.AllGroups()
        ))
    }

    @Test
    fun `cannot combine ALL group option with group list`()
    {
        assertThatThrownBy { AddUserToGroupOptions.parseArgs(listOf("username", "ALL", "group")) }
                .isInstanceOf(ActionException::class.java)
    }

    @Test
    fun `throws exception if not enough arguments are supplied`()
    {
        assertThatThrownBy { AddUserToGroupOptions.parseArgs(emptyList()) }
        assertThatThrownBy { AddUserToGroupOptions.parseArgs(listOf("username")) }
    }
}