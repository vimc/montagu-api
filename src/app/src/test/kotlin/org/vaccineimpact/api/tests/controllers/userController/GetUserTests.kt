package org.vaccineimpact.api.tests.controllers.userController

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions
import org.junit.Test
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.controllers.UserController
import org.vaccineimpact.api.app.repositories.UserRepository
import org.vaccineimpact.api.app.security.OneTimeTokenGenerator
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.User
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.ReifiedRole
import org.vaccineimpact.api.models.permissions.RoleAssignment
import org.vaccineimpact.api.security.InternalUser
import org.vaccineimpact.api.security.UserProperties
import org.vaccineimpact.api.test_helpers.MontaguTests

class GetUserTests : MontaguTests()
{

    @Test
    fun `getUser returns user without roles`()
    {
        val userName = "test"
        val expectedUser = User("test", "test name", "test@test.com", null)

        val roles = listOf(
                ReifiedRole("user", Scope.Global()),
                ReifiedRole("member", Scope.Specific("modelling-group", "IC-Garske"))
        )

        val internalUser = InternalUser(UserProperties("test", "test name", "test@test.com", null, null), roles, listOf())
        val permissionSet = PermissionSet()

        val repo = mock<UserRepository> {
            on { this.getUserByUsername(userName) } doReturn internalUser
        }

        val context = mock<ActionContext> {
            on { params(":username") } doReturn userName
            on { permissions } doReturn permissionSet
        }

        val sut = UserController(context, repo, mock<OneTimeTokenGenerator>())
        Assertions.assertThat(sut.getUser()).isEqualToComparingFieldByField(expectedUser)
    }

    @Test
    fun `getUser returns user with all roles`()
    {
        val userName = "test"

        val expectedRoles = listOf(
                RoleAssignment("user", null, ""),
                RoleAssignment("member", "modelling-group", "IC-Garske")
        )

        val roles = listOf(
                ReifiedRole("user", Scope.Global()),
                ReifiedRole("member", Scope.Specific("modelling-group", "IC-Garske"))
        )

        val internalUser = InternalUser(UserProperties("test", "test name", "test@test.com", null, null), roles, listOf())

        val permissionSet = PermissionSet("*/roles.read")

        val repo = mock<UserRepository> {
            on { getUserByUsername(userName) } doReturn internalUser
        }

        val context = mock<ActionContext> {
            on { params(":username") } doReturn userName
            on { permissions } doReturn permissionSet
        }

        val sut = UserController(context, repo, mock<OneTimeTokenGenerator>())
        val actualRoles = sut.getUser().roles
        Assertions.assertThat(actualRoles).hasSameElementsAs(expectedRoles)
    }

    @Test
    fun `getUser returns user with specific scoped roles`()
    {
        val userName = "test"

        val roles = listOf(
                ReifiedRole("user", Scope.Global()),
                ReifiedRole("member", Scope.Specific("modelling-group", "IC-Garske")),
                ReifiedRole("member", Scope.Specific("modelling-group", "group-2")),
                ReifiedRole("member", Scope.Specific("foo", "IC-Garske"))
        )

        val internalUser = InternalUser(UserProperties("test", "test name", "test@test.com", null, null), roles, listOf())

        val permissionSet = PermissionSet("modelling-group:IC-Garske/roles.read")

        val repo = mock<UserRepository> {
            on { this.getUserByUsername(userName) } doReturn internalUser
        }

        val context = mock<ActionContext> {
            on { params(":username") } doReturn userName
            on { permissions } doReturn permissionSet
        }

        val expectedRoles = listOf(RoleAssignment("member", "modelling-group", "IC-Garske"))

        val sut = UserController(context, repo, mock<OneTimeTokenGenerator>())
        val actualRoles = sut.getUser().roles

        Assertions.assertThat(actualRoles).hasSameElementsAs(expectedRoles)
    }

    @Test
    fun `getUsers returns all users without roles`()
    {
        val users = listOf(User("test1", "Test Name 1   ", "test1@test.com", null))

        val repo = mock<UserRepository> {
            on { this.all() } doReturn users
        }

        val context = mock<ActionContext> {
            on { permissions } doReturn PermissionSet()
        }

        val sut = UserController(context, repo, mock<OneTimeTokenGenerator>())
        val actualUsers = sut.getUsers()

        Assertions.assertThat(actualUsers).hasSameElementsAs(users)
    }

    @Test
    fun `getUsers returns all users with all roles`()
    {
        val expectedRoles = listOf(
                RoleAssignment("user", null, null),
                RoleAssignment("member", "modelling-group", "IC-Garske")
        )

        val users = listOf(User("test", "test name", "test@test.com", null, expectedRoles))
        val permissionSet = PermissionSet("*/roles.read")

        val repo = mock<UserRepository> {
            on { allWithRoles() } doReturn users
        }

        val context = mock<ActionContext> {
            on { permissions } doReturn permissionSet
        }

        val sut = UserController(context, repo, mock<OneTimeTokenGenerator>())
        val actualRoles = sut.getUsers()[0].roles
        Assertions.assertThat(actualRoles).hasSameElementsAs(expectedRoles)
    }

    @Test
    fun `getUsers returns all users with specific scoped roles`()
    {
        val users = listOf(User("test", "test name", "test@test.com", null,
                listOf(
                        RoleAssignment("user", null, null),
                        RoleAssignment("member", "modelling-group", "IC-Garske"),
                        RoleAssignment("member", "modelling-group", "group2"),
                        RoleAssignment("member", "foo", "IC:Garske")
                )))

        val permissionSet = PermissionSet("modelling-group:IC-Garske/roles.read")

        val repo = mock<UserRepository> {
            on { this.allWithRoles() } doReturn users
        }

        val context = mock<ActionContext> {
            on { permissions } doReturn permissionSet
        }

        val expectedRoles = listOf(RoleAssignment("member", "modelling-group", "IC-Garske"))

        val sut = UserController(context, repo, mock<OneTimeTokenGenerator>())
        val actualRoles = sut.getUsers()[0].roles

        Assertions.assertThat(actualRoles).hasSameElementsAs(expectedRoles)
    }

    @Test
    fun `getCurrentUser returns context user with no roles`()
    {
        val context = mock<ActionContext>{
            on {  username } doReturn "test"
        }

        val roles = listOf(
                ReifiedRole("user", Scope.Global()),
                ReifiedRole("member", Scope.Specific("modelling-group", "IC-Garske"))
        )
        val testInternalUser = InternalUser(UserProperties("test", "test name", "test@test.com", null, null),
                roles, listOf())


        val repo = mock<UserRepository> {
            on { this.getUserByUsername("test") } doReturn testInternalUser
        }

        val sut = UserController(context, repo, mock())

        val result = sut.getCurrentUser();

        Assertions.assertThat(result.username).isEqualTo("test")
        Assertions.assertThat(result.email).isEqualTo("test@test.com")
        Assertions.assertThat(result.name).isEqualTo("test name")
        Assertions.assertThat(result.roles).isNull()
    }
}
