import io.qameta.allure.gradle.base.AllureExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    jacoco
    id("io.qameta.allure") version "2.11.2"
}

val allureVersion = "2.22.1"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

configure<AllureExtension> {
    adapter.autoconfigure.set(true)
    version.set(allureVersion)

    adapter.frameworks.junit5.enabled.set(true)
    adapter.frameworks.junit5.adapterVersion.set(allureVersion)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")

    testImplementation("io.qameta.allure:allure-kotlin-model:2.2.4")
    testImplementation("io.qameta.allure:allure-kotlin-commons:2.4.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation("org.slf4j:slf4j-simple:2.0.7")

    testImplementation("io.mockk:mockk:1.13.5")

}

tasks.test {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport", "allureReport")
}

application {
    mainClass.set("MainKt")
}