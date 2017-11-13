package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.app.ActionContext
import org.vaccineimpact.api.app.controllers.endpoints.Endpoint
import org.vaccineimpact.api.app.controllers.endpoints.multiRepoEndpoint
import org.vaccineimpact.api.app.controllers.endpoints.oneRepoEndpoint
import org.vaccineimpact.api.app.controllers.endpoints.secured
import org.vaccineimpact.api.app.errors.MissingRequiredPermissionError
import org.vaccineimpact.api.app.models.CreateUser
import org.vaccineimpact.api.app.postData
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.app.repositories.UserRepository
import org.vaccineimpact.api.emails.EmailManager
import org.vaccineimpact.api.emails.NewUserEmail
import org.vaccineimpact.api.emails.getEmailManager
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.User
import org.vaccineimpact.api.models.encompass
import org.vaccineimpact.api.models.permissions.AssociateRole
import org.vaccineimpact.api.models.permissions.RoleAssignment
import spark.route.HttpMethod

class UserController(
        context: ControllerContext,
        private val emailManager: EmailManager = getEmailManager()
) : AbstractController(context)
{
    override val urlComponent = "/users"
    override fun endpoints(repos: RepositoryFactory): List<Endpoint<*>> = listOf(
            oneRepoEndpoint("/roles/all/",
                    this::getGlobalRoles, repos, { it.user }, method = HttpMethod.get)
                    .secured(setOf("*/roles.read")),
            oneRepoEndpoint("/:username/", this::getUser, repos, { it.user }).secured(setOf("*/users.read")),
            oneRepoEndpoint("/", this::getUsers, repos, { it.user }).secured(setOf("*/users.read")),
            multiRepoEndpoint("/", this::createUser, repos, method = HttpMethod.post).secured(setOf("*/users.create")),
            oneRepoEndpoint("/:username/actions/associate_role/",
                    this::modifyUserRole, repos, { it.user }, method = HttpMethod.post).secured()
    )

    fun modifyUserRole(context: ActionContext, repo: UserRepository): String
    {
        val userName = userName(context)
        val associateRole = context.postData<AssociateRole>()

        val scope = if (associateRole.scopePrefix.isNullOrEmpty())
        {
            Scope.Global()
        }
        else
        {
            Scope.parse("${associateRole.scopePrefix}:${associateRole.scopeId}")
        }

        val roleWritingScopes = roleWritingScopes(context)

        if (!roleWritingScopes.any({ it.encompasses(scope) }))
        {
            throw MissingRequiredPermissionError(setOf("${scope.toString()}/roles.write"))
        }

        repo.modifyUserRole(userName, associateRole)
        return okayResponse()
    }

    fun getUser(context: ActionContext, repo: UserRepository): User
    {
        val userName = userName(context)
        val roleReadingScopes = roleReadingScopes(context)

        val user = repo.getUserByUsername(userName)
        if (roleReadingScopes.any())
        {
            val allRoles = repo.getRolesForUser(userName)
            val roles = filteredRoles(allRoles, roleReadingScopes)
            return user.copy(roles = roles)
        }
        else
        {
            return user
        }
    }

    fun getUsers(context: ActionContext, repo: UserRepository): List<User>
    {
        val roleReadingScopes = roleReadingScopes(context)

        if (roleReadingScopes.any())
        {
            val users = repo.allWithRoles().toList()

            return users.map {
                it.copy(roles = filteredRoles(it.roles, roleReadingScopes))
            }
        }
        else
        {
            return repo.all().toList()
        }
    }

    fun getGlobalRoles(context: ActionContext, repo: UserRepository): List<String>
    {
        return repo.globalRoles()
    }

    fun createUser(context: ActionContext, repos: Repositories): String
    {
        val user = context.postData<CreateUser>()
        repos.user.addUser(user)

        val token = getSetPasswordToken(user.username, context, repos.token)
        emailManager.sendEmail(NewUserEmail(user, token), user)

        return objectCreation(context, urlComponent + "/${user.username}/")
    }

    private fun userName(context: ActionContext): String = context.params(":username")

    private fun roleReadingScopes(context: ActionContext) = context.permissions
            .filter { it.name == "roles.read" }
            .map { it.scope }

    private fun roleWritingScopes(context: ActionContext) = context.permissions
            .filter { it.name == "roles.write" }
            .map { it.scope }

    private fun filteredRoles(allRoles: List<RoleAssignment>?, roleReadingScopes: Iterable<Scope>) =
            allRoles?.filter { roleReadingScopes.encompass(Scope.parse(it)) }
}