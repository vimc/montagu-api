package org.vaccineimpact.api.security

import org.jooq.exception.DataAccessException
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.Tables.ROLE
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.ReifiedRole
import kotlin.system.exitProcess

fun addRole(args: List<String>)
{
    AddRoleOptions.parseArgs(args).run {
        JooqContext().use { db ->
            try
            {
                db.ensureUserHasRole(username, ReifiedRole(roleName, scope))
            }
            catch (e: UnknownRoleException)
            {
                throw ActionException("No role exists with name '$roleName' and scope prefix '${scope.databaseScopePrefix}'")
            }
        }
        println("Gave role $scope/$roleName to '$username'")
    }
}


fun addAllGlobalRoles(args: List<String>)
{
    if (args.size != 1)
    {
        println("Usage: ./user.sh addAllGlobalRoles USERNAME")
        exitProcess(0)
    }

    val username = args[0]

    JooqContext().use { db ->
        try
        {
            db.dsl.select(ROLE.NAME)
                    .from(ROLE)
                    .where(ROLE.SCOPE_PREFIX.isNull)
                    .fetchInto(String::class.java)
                    .map{
                        db.ensureUserHasRole(username, ReifiedRole(it, Scope.Global()))
                    }
        }
        catch (e: DataAccessException)
        {
            throw ActionException("Failed to add all global roles to user '$username'. Check that user '$username' exists")
        }
    }
    println("Gave all global roles to '$username'")
}