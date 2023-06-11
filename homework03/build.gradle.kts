plugins {
    application
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    runtimeOnly("io.kotest:kotest-framework-api-jvm:5.6.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    val kotestVersion = "5.6.2"
    implementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    implementation("io.kotest:kotest-assertions-core:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}