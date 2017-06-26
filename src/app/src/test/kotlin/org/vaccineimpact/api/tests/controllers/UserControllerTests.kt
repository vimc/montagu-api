package org.vaccineimpact.api.tests.controllers

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.app.ActionContext
import org.vaccineimpact.api.app.controllers.ControllerContext
import org.vaccineimpact.api.app.controllers.UserController
import org.vaccineimpact.api.app.repositories.UserRepository
import org.vaccineimpact.api.models.User
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.RoleAssignment

class UserControllerTests : ControllerTests<UserController>()
{
    override fun makeController(controllerContext: ControllerContext)
            = UserController(controllerContext)

    @Test
    fun `getUser returns user without roles`()
    {
        val userName = "test"
        val user = User("test", "test name", "test@test.com", null, null)

        val permissionSet = PermissionSet()

        val controllerContext = mockControllerContext(mock<UserRepository> {
            on { this.getUserByUsername(userName) } doReturn user
        })

        val context = mock<ActionContext> {
            on { params(":username") } doReturn userName
            on { permissions } doReturn permissionSet
        }

        val controller = UserController(controllerContext)
        assertThat(controller.getUser(context)).isEqualToComparingFieldByField(user)
    }

    @Test
    fun `getUser returns user with all roles`()
    {
        val userName = "test"

        val user = User("test", "test name", "test@test.com", null, null)
        val expectedRoles = listOf(
                RoleAssignment("user", null, null),
                RoleAssignment("member", "modelling-group", "IC-Garske")
        )

        val permissionSet = PermissionSet("*/roles.read")

        val controllerContext = mockControllerContext(mock<UserRepository> {
            on { getUserByUsername(userName) } doReturn user
            on { getRolesForUser(userName) } doReturn expectedRoles
        })

        val context = mock<ActionContext> {
            on { params(":username") } doReturn userName
            on { permissions } doReturn permissionSet
        }

        val controller = UserController(controllerContext)
        val actualRoles = controller.getUser(context).roles
        assertThat(actualRoles).hasSameElementsAs(expectedRoles)
    }

    @Test
    fun `getUser returns user with specific scoped roles`()
    {
        val userName = "test"

        val user = User("test", "test name", "test@test.com", null, null)
        val roles = listOf(
                RoleAssignment("user", null, null),
                RoleAssignment("member", "modelling-group", "IC-Garske"),
                RoleAssignment("member", "modelling-group", "group2"),
                RoleAssignment("member", "foo", "IC:Garske")
        )

        val permissionSet = PermissionSet("modelling-group:IC-Garske/roles.read")

        val controllerContext = mockControllerContext(mock<UserRepository> {
            on { this.getUserByUsername(userName) } doReturn user
            on { this.getRolesForUser(userName) } doReturn roles
        })

        val context = mock<ActionContext> {
            on { params(":username") } doReturn userName
            on { permissions } doReturn permissionSet
        }

        val expectedRoles = listOf(RoleAssignment("member", "modelling-group", "IC-Garske"))

        val controller = UserController(controllerContext)
        val actualRoles = controller.getUser(context).roles

        assertThat(actualRoles).hasSameElementsAs(expectedRoles)
    }

    @Test
    fun `getUsers returns all users without roles`()
    {
        val users = listOf(User("test1", "Test Name 1   ", "test1@test.com", null, null))

        val controllerContext = mockControllerContext(mock<UserRepository> {
            on { this.all() } doReturn users
        })

        val controller = UserController(controllerContext)
        val actualUsers = controller.getUsers(mock<ActionContext>())

        assertThat(actualUsers).hasSameElementsAs(users)
    }

    private fun mockControllerContext(repo: UserRepository)
            = mockControllerContext(RepositoryMock({ it.user }, repo))
}
