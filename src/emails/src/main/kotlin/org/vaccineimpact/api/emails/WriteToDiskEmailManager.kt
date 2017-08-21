package org.vaccineimpact.api.emails

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.vaccineimpact.api.security.MontaguUser
import java.io.File
import java.time.Instant

class WriteToDiskEmailManager : EmailManager
{
    override fun sendEmail(data: EmailData, recipient: MontaguUser)
    {
        val text = data.text()
        outputDirectory.mkdirs()
        val file = File(outputDirectory, Instant.now().toString())
        file.writeText(text)
        logger.info("Wrote email to ${file.absolutePath}")
    }

    companion object
    {
        private val logger: Logger = LoggerFactory.getLogger(WriteToDiskEmailManager::class.java)
        val outputDirectory = File("/tmp/montagu_emails")

        fun cleanOutputDirectory()
        {
            outputDirectory.listFiles().forEach { it.delete() }
        }
    }
}