import com.beust.klaxon.json
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.db.direct.addTouchstone
import org.vaccineimpact.api.test_helpers.DatabaseTest

class TouchstoneTests : DatabaseTest()
{
    @Test
    fun `can get touchstones`()
    {
        validate("/touchstones/") against "Touchstones" given {
            it.addTouchstone("test", 6, "description-1", "open", 1990..2070, addName = true, addStatus = true)
            it.addTouchstone("example", 1, "description-2", "in-preparation", 1900..2100, addName = true, addStatus = true)
        } andCheckArray {
            assertThat(it.count()).isEqualTo(2)
            assertThat(it).contains(json { obj(
                    "id" to "test-6",
                    "name" to "test",
                    "version" to 6,
                    "status" to "open",
                    "years" to obj("start" to 1990, "end" to 2070),
                    "description" to "description-1"
            )})
        }
    }
}