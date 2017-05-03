import com.fasterxml.jackson.databind.JsonNode
import com.github.fge.jackson.JsonLoader
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration
import com.github.fge.jsonschema.core.load.uri.URITranslatorConfiguration
import com.github.fge.jsonschema.main.JsonSchemaFactory
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat

class SchemaValidator
{
    private val schemaFactory = makeSchemaFactory()
    private val responseSchema = readSchema("Response")

    fun validate(schemaName: String, jsonAsString: String)
    {
        val json = JsonLoader.fromString(jsonAsString)
        // Everything must meet the basic response schema
        checkResultSchema(json, jsonAsString, "success")
        // Then use the more specific schema on the data portion
        val data = json["data"]
        val schema = readSchema(schemaName)
        assertValidates(schema, data)
    }

    fun validateError(jsonAsString: String, errorText: String? = null)
    {
        val json = JsonLoader.fromString(jsonAsString)
        checkResultSchema(json, jsonAsString, "failure", errorText = errorText)
    }

    private fun checkResultSchema(json: JsonNode, jsonAsString: String, expectedStatus: String, errorText: String? = null)
    {
        assertValidates(responseSchema, json)
        val status = json["status"].textValue()
        assertThat(status)
                .`as`(errorText ?: "Check that the following response has status '$expectedStatus': $jsonAsString")
                .isEqualTo(expectedStatus)
    }

    private fun readSchema(name: String) = JsonLoader.fromResource("/spec/$name.schema.json")

    private fun assertValidates(schema: JsonNode, json: JsonNode)
    {
        val report = schemaFactory.getJsonSchema(schema).validate(json)
        if (!report.isSuccess)
        {
            Assertions.fail("JSON failed schema validation. Attempted to validate: $json against $schema. Report follows: $report")
        }
    }

    private fun makeSchemaFactory(): JsonSchemaFactory
    {
        val namespace = "resource:/spec/"
        val uriTranslatorConfig = URITranslatorConfiguration
                .newBuilder()
                .setNamespace(namespace)
                .freeze()
        val loadingConfig = LoadingConfiguration.newBuilder()
                .setURITranslatorConfiguration(uriTranslatorConfig)
                .freeze()
        return JsonSchemaFactory.newBuilder()
                .setLoadingConfiguration(loadingConfig)
                .freeze()
    }
}