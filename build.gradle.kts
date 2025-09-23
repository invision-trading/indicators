import org.jreleaser.model.Active.ALWAYS
import org.jreleaser.model.Active.NEVER
import java.time.Duration.ofHours

plugins {
    `java-library`
    id("io.freefair.lombok") version "8.14"
    `maven-publish`
    id("org.jreleaser") version "1.20.0"
}

group = "trade.invision"
version = "1.2.0"

repositories {
    mavenCentral()
}

val numVersion = "1.9.0"

dependencies {
    implementation("org.jetbrains", "annotations", "26.0.2")
    implementation("com.google.guava", "guava", "33.4.8-jre")
    implementation("com.github.ben-manes.caffeine", "caffeine", "3.2.2")
    api("trade.invision", "num", numVersion)
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withJavadocJar()
    withSourcesJar()
}

tasks.javadoc.configure {
    options {
        (this as CoreJavadocOptions).addBooleanOption("Xdoclint:none", true)
        addStringOption("link",
                "https://docs.oracle.com/en/java/javase/${java.targetCompatibility.majorVersion}/docs/api/")
        addStringOption("link", "https://javadoc.io/doc/trade.invision/num/${numVersion}/")
    }
}

val stagingDeployDirectory = file("build/staging-deploy")

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "indicators"
            from(components["java"])
            pom {
                name = "indicators"
                description = "A Java library that provides a variety of technical indicators for time series data, " +
                        "such as OHLC bars."
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
            url = uri(stagingDeployDirectory)
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
                    stagingRepository(stagingDeployDirectory.path)
                    maxRetries = ofHours(1).toSeconds().toInt()
                    retryDelay = ofHours(1).toSeconds().toInt()
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

tasks.build { mustRunAfter(tasks.clean) }
tasks.publish { mustRunAfter(tasks.build) }
tasks.jreleaserFullRelease { dependsOn(tasks.clean, tasks.build, tasks.publish) }
