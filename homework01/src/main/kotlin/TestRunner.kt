package org.otus

interface TestRunner {
    fun <T> runTest(steps: T, test: () -> Unit)
}