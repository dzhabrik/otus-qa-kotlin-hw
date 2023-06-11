package org.otus

import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    val test = Test()
    val runner = TestRunnerImpl()

    runner.runTest(test) { test.isEvenTest() }
    runner.runTest(test) { test.isOddTest() }
    runner.runTest(test) { test.nonNullTest() }
    runner.runTest(test) { test.isNullTest() }
}

class Test {

    private val errorMsgTemplate: String = "%s. Actual (i) value is %s"

    private var i by Delegates.notNull<Int>()
    private var startTime by Delegates.notNull<Long>()

    fun beforeTestSetStartTime() {
        println("beforeTestSetStartTime called")
        startTime = System.currentTimeMillis()
    }

    fun beforeTestInitVar() {
        println()
        println("beforeTestInitVar called")
        i = Random.nextInt()
    }

    fun afterTestCalculateExecutionTime() {
        print("afterTestCalculateExecutionTime called.")
        val testExecutionTime: Long = System.currentTimeMillis() - startTime
        startTime = 0L
        println(" Execution time was ${testExecutionTime.toDuration(DurationUnit.MILLISECONDS)}")
    }

    fun afterTestSetZero() {
        println("afterTestSetZero called")
        i = 0
    }

    fun isEvenTest() {
        Thread.sleep(1000)
        println("isEvenTest message")
        assert(i % 2 == 0) { errorMsgTemplate.format("Num is not even", i) }
    }

    fun isOddTest() {
        Thread.sleep(2000)
        println("isOddTest message")
        assert(i % 2 > 0) { errorMsgTemplate.format("Num is not odd", i) }
    }

    fun nonNullTest() {
        Thread.sleep(4000)
        println("nonNullTest message")
        assert(i != null) { errorMsgTemplate.format("Num is null", i) }
    }

    //At least one test will fail
    fun isNullTest() {
        println("isNullTest message")
        assert(i == null) { errorMsgTemplate.format("Num is not null", i) }
    }
}