import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MyTests : FunSpec({

    test("My test1 Passed") {
        println("Start test1")
        1 + 2 shouldBe 3
    }

    test("My test2 Failed") {
        println("Start test2")
        1 + 2 shouldBe 4
    }
    test("My test3 Exception") {
        println("Start test3")
        throw IllegalStateException("some exception thrown")
        //1 + 2 shouldBe 4
    }
})