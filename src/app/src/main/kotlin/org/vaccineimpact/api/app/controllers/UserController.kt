package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.app.app_start.Controller
import org.vaccineimpact.api.app.clients.OkHttpOrderlyWebAPIClient
import org.vaccineimpact.api.app.clients.OrderlyWebAPIClient
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.context.postData
import org.vaccineimpact.api.app.errors.MissingRequiredPermissionError
import org.vaccineimpact.api.app.models.CreateUser
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.UserRepository
import org.vaccineimpact.api.app.security.OneTimeTokenGenerator
import org.vaccineimpact.api.emails.EmailManager
import org.vaccineimpact.api.emails.NewUserEmail
import org.vaccineimpact.api.emails.getEmailManager
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.User
import org.vaccineimpact.api.models.encompass
import org.vaccineimpact.api.models.permissions.AssociateRole
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.RoleAssignment
import org.vaccineimpact.api.security.CookieName

class UserController(
        context: ActionContext,
        private val userRepository: UserRepository,
        private val oneTimeTokenGenerator: OneTimeTokenGenerator,
        private val emailManager: EmailManager = getEmailManager(),
        private val orderlyWebAPIClient: OrderlyWebAPIClient = OkHttpOrderlyWebAPIClient.create(context.authenticationToken()!!)
) : Controller(context)
{

    constructor(context: ActionContext, repositories: Repositories)
            : this(context, repositories.user, OneTimeTokenGenerator(repositories.token))


    fun saveConfidentialityAgreement(): String
    {
        val userName = context.username!!
        userRepository.saveConfidentialityAgreement(userName)
        return okayResponse()
    }

    fun hasAgreedConfidentiality(): Boolean
    {
        val userName = context.username!!
        return userRepository.hasAgreedConfidentiality(userName)
    }

    fun getReportReaders(): List<User>
    {
        val reportName = context.params(":report")
        return userRepository.reportReaders(reportName)
    }

    fun modifyUserRole(): String
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
            throw MissingRequiredPermissionError(PermissionSet("$scope/roles.write"))
        }

        userRepository.modifyUserRole(userName, associateRole)
        return okayResponse()
    }

    fun createUser(): String
    {
        val user = context.postData<CreateUser>()
        userRepository.addUser(user)

        val newUser = userRepository.getUserByUsername(user.username)
        val token = oneTimeTokenGenerator.getSetPasswordToken(newUser)

        emailManager.sendEmail(NewUserEmail(user, token), user)

        orderlyWebAPIClient.addUser(user.email, user.username, user.name)

        return objectCreation(context, "/users/${user.username}/")
    }

    fun getUser(): User
    {
        val userName = userName(context)
        val roleReadingScopes = roleReadingScopes(context)

        val internalUser = userRepository.getUserByUsername(userName)
        if (roleReadingScopes.any())
        {
            val userWithAllRoles = internalUser.toUser()
            return userWithAllRoles.copy(roles = filteredRoleAssignments(userWithAllRoles.roles, roleReadingScopes))
        }
        else
        {
            return internalUser.toUser().copy(roles = null)
        }
    }

    fun getCurrentUser(): User
    {
        val userName = context.username!!
        val includePermissions =
                context.request.queryParamOrDefault("includePermissions", "false") == "true"
        val internalUser = userRepository.getUserByUsername(userName)

        return internalUser.toUser(includePermissions).copy(roles = null) //don't return any role information back to the current user
    }

    fun verifyCurrentUser(): String
    {
        println("verifying current user")
        val userName = context.username!!
        context.addResponseHeader("X-Remote-User", userName)
        return okayResponse()
    }

    fun getUsers(): List<User>
    {
        val roleReadingScopes = roleReadingScopes(context)

        if (roleReadingScopes.any())
        {
            val users = userRepository.allWithRoles().toList()

            return users.map {
                it.copy(roles = filteredRoleAssignments(it.roles, roleReadingScopes))
            }
        }
        else
        {
            return userRepository.all().toList()
        }
    }

    fun getGlobalRoles(): List<String>
    {
        return userRepository.globalRoles()
    }

    private fun userName(context: ActionContext): String = context.params(":username")

    private fun roleReadingScopes(context: ActionContext) = context.permissions
            .filter { it.name == "roles.read" }
            .map { it.scope }

    private fun roleWritingScopes(context: ActionContext) = context.permissions
            .filter { it.name == "roles.write" }
            .map { it.scope }

    private fun filteredRoleAssignments(allRoles: List<RoleAssignment>?, roleReadingScopes: Iterable<Scope>) =
            allRoles?.filter { roleReadingScopes.encompass(Scope.parse(it)) }

}