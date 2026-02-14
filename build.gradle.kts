import org.gradle.kotlin.dsl.annotationProcessor

plugins {
    id("java")
    id("io.freefair.lombok") version "8.14"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.rest-assured:rest-assured:5.5.5")
    testImplementation("io.rest-assured:json-schema-validator:5.5.0")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.35.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
}

tasks.test {
    useJUnitPlatform()

//    maxParallelForks = 4
//
//    systemProperty("junit.jupiter.execution.parallel.enabled", "true")
//    systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
//    systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "concurrent")
}