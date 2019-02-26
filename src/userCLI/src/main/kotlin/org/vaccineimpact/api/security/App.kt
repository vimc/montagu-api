package org.vaccineimpact.api.security

import kotlin.system.exitProcess

fun main(args: Array<String>)
{
    val action = getAction(args)
    try
    {
        val remainder = args.drop(1)
        when (action)
        {
            Action.add -> addUser(remainder)
            Action.addRole -> addRole(remainder)
            Action.addUserToGroup -> addToGroup(remainder)
            Action.sendTestEmail -> sendTestEmail(remainder)
            Action.addAllGlobalRoles -> addAllGlobalRoles(remainder)
        }
    }
    catch (e: ActionException)
    {
        println(e.message)
        exitProcess(-1)
    }
}

fun getAction(args: Array<String>): Action
{
    if (args.isEmpty())
    {
        println("An action is required. ./user.sh ACTION")
        println("ACTION must be one of " + enumValues<Action>().joinToString())
        exitProcess(0)
    }
    return enumValueOf(args.first())
}