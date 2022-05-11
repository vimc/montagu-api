package org.vaccineimpact.api.generateDatabaseInterface

import org.jooq.codegen.GenerationTool

fun main(args: Array<String>)
{
    CodeGenerator().run()
}

class CodeGenerator
{
    fun run()
    {
        generate("main-database.xml")
    }

    private fun generate(configFileName: String)
    {
        val url = CodeGenerator::class.java.classLoader.getResource(configFileName)
        val config = url.openStream().use {
            GenerationTool.load(it)
        }
        GenerationTool().run(config)
    }
}