package org.vaccineimpact.api.app.repositories

import org.vaccineimpact.api.models.User
import org.vaccineimpact.api.models.permissions.RoleAssignment
import org.vaccineimpact.api.security.MontaguUser

interface UserRepository : Repository
{
    fun getMontaguUserByEmail(email: String): MontaguUser?
    fun getUserByUsername(username: String): User
    fun getRolesForUser(username: String): List<RoleAssignment>
    fun all(): Iterable<User>
}