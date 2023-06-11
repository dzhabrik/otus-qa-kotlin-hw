import io.kotest.core.annotation.AutoScan
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult

@AutoScan
class RepeatOnFailureExtension : TestCaseExtension {

    private val maxRetries: Int = 3

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        var retries = 0
        var result: TestResult = execute.invoke(testCase)
        while (result.isErrorOrFailure && retries != maxRetries) {
            result = execute.invoke(testCase.copy())
            retries++
        }
        return result
    }
}