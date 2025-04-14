import org.jreleaser.model.Active.ALWAYS
import org.jreleaser.model.Active.NEVER

plugins {
    `java-library`
    id("io.freefair.lombok") version "8.13.1"
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "indicators"
            from(components["java"])
            pom {
                name = "indicators"
                description = "A Java library that provides a variety of statistical, utility, and technical " +
                        "indicators for time series data, such as OHLC bars."
                url = "https://github.com/invision-trading/indicators"
                inceptionYear = "2025"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }
                developers {
                    developer {
                        id = "Petersoj"
                        name = "Jacob Peterson"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/invision-trading/indicators.git"
                    developerConnection = "scm:git:ssh://github.com/invision-trading/indicators.git"
                    url = "https://github.com/invision-trading/indicators"
                }
            }
        }
    }
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

jreleaser {
    signing {
        active = ALWAYS
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active = ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
                    // Timeout of 1 hour.
                    maxRetries = 60
                    retryDelay = 60
                }
            }
        }
    }
    release {
        github {
            uploadAssets = NEVER
        }
    }
}
