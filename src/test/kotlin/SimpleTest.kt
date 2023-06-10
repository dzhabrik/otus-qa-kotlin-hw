import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MyTests : FunSpec({

    test("My test") {
        println("Start test")
        1 + 2 shouldBe 4
    }

    test("My test1") {
        println("Start test")
        1 + 2 shouldBe 3
    }
})