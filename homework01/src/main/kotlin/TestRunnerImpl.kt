package org.otus

import kotlin.reflect.full.declaredFunctions

class TestRunnerImpl : TestRunner {
    override fun <T> runTest(steps: T, test: () -> Unit) {
        val mSteps = when (steps) {
            null -> throw NullPointerException("Class with callbacks is required!")
            else -> steps
        }

        val mStepsFunctions = mSteps::class.declaredFunctions

        mStepsFunctions.filter { kFunction -> kFunction.name.contains("before") }
            .forEach { it.call(mSteps) }

        try {
            test.invoke()
        } catch (e: AssertionError) {
            e.printStackTrace()
        }

        mStepsFunctions.filter { kFunction -> kFunction.name.contains("after") }
            .forEach { it.call(mSteps) }
    }
}