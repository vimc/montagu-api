import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.security.SodiumPasswordEncoder

class SodiumTests
{
    @Test
    fun `same password that was hashed verifies`()
    {
        val encoder = SodiumPasswordEncoder()
        val testPassword = "this is a password"

        val hashedPw = encoder.encode(testPassword)
        assertThat(encoder.matches(testPassword, hashedPw)).isTrue()
    }

    @Test
    fun `different password does not verify`()
    {
        val encoder = SodiumPasswordEncoder()
        val testPassword = "this is a password"

        val hashedPw = encoder.encode(testPassword)
        assertThat(encoder.matches("different password", hashedPw)).isFalse()
    }
}
