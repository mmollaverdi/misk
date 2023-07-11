package detektive.javacompat

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@KotlinCoreEnvironmentTest
internal class AnnotatePublicApisWithJvmOverloadsTest(private val env: KotlinCoreEnvironment) {

  @ParameterizedTest
  @MethodSource("errorTestCases")
  fun reportsError(testCase: TestCase) {
    val findings =
      AnnotatePublicApisWithJvmOverloads(Config.empty).compileAndLintWithContext(env, testCase.code)

    assertThat(findings).hasSize(1)
    with(findings[0]) {
      assertThat(issue.severity).isEqualTo(Severity.Defect)
      assertThat(issue.id).isEqualTo("AnnotatePublicApisWithJvmOverloads")
      assertThat(entity.signature).contains("doIt")
      assertThat(message).contains(
        "Public API function 'doIt' with default arguments, but without @JvmOverloads annotation"
      )
    }
  }

  @ParameterizedTest
  @MethodSource("noErrorTestCases")
  fun doesntReportsError(testCase: TestCase) {
    val findings =
      AnnotatePublicApisWithJvmOverloads(Config.empty).compileAndLintWithContext(env, testCase.code)

    assertThat(findings).hasSize(0)
  }

  companion object {
    data class TestCase(val description: String, val code: String)

    @JvmStatic
    fun errorTestCases() = listOf(
      TestCase(
        description = "Public function with any default arguments, but without @JvmOverloads",
        code = """
        class A {
          public fun doIt(x: String = "", y: Int) {}
        }
        """
      )
    )

    @JvmStatic
    fun noErrorTestCases() = listOf(
      TestCase(
        description = "Public function without default arguments",
        code = """
        class A {
          public fun doIt(x: String, y: Int) {}
        }
        """
      ),
      TestCase(
        description = "Public function with default arguments and with @JvmOverloads",
        code = """
        class A {
          @JvmOverloads
          public fun doIt(x: String = "", y: Int) {}
        }
        """
      ),
      TestCase(
        description = "Public function in an interface",
        code = """
        interface A {
          fun doIt(x: String = "")
        }
        """
      )
    )
  }
}
