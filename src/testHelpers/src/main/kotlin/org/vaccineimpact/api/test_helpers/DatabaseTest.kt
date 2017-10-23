package org.vaccineimpact.api.test_helpers

import org.junit.After
import org.junit.Before
import org.vaccineimpact.api.db.Config
import org.vaccineimpact.api.db.JooqContext

abstract class DatabaseTest : MontaguTests()
{
    private val templateDbName = Config["testdb.template_name"]
    private val dbName = Config["db.name"]

    @Before
    fun createDatabase()
    {
        JooqContext(dbName = "postgres").use {
            it.dsl.query("CREATE DATABASE $dbName TEMPLATE $templateDbName;").execute()
        }
        DatabaseCreationHelper.checkDatabaseExists(dbName)
    }

    @After
    fun dropDatabase()
    {
        org.vaccineimpact.api.db.JooqContext(dbName = "postgres").use {
            it.dsl.query("DROP DATABASE $dbName").execute()
        }
    }
}