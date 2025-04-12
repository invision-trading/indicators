plugins {
    `java-library`
    `maven-publish`
    id("org.jreleaser") version "1.17.0"
}

group = "trade.invision"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Jetbrains Annotations
    implementation("org.jetbrains", "annotations", "24.1.0")

    // Google Guava
    implementation("com.google.guava", "guava", "33.2.1-jre")

    // num
    api("trade.invision", "num", "1.2.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withJavadocJar()
    withSourcesJar()
}

tasks.javadoc.configure {
    options {
        (this as CoreJavadocOptions).addBooleanOption("Xdoclint:none", true)
        addStringOption("link", "https://docs.oracle.com/en/java/javase/21/docs/api/")
    }
}
