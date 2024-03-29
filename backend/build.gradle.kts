import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.regex.Pattern.compile

plugins {

    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springdoc.openapi-gradle-plugin") version "1.3.4"

    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    kotlin("plugin.jpa") version "1.5.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.vladmihalcea:hibernate-types-52:2.16.2")
    implementation("commons-io:commons-io:2.11.0")
    implementation("io.minio:minio:8.4.2")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
    implementation("org.springdoc:springdoc-openapi-security:1.6.9")
    implementation("org.flywaydb:flyway-core")

    runtimeOnly("org.postgresql:postgresql")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    compile("org.springframework.boot:spring-boot-configuration-processor")
    compile("io.minio:minio:8.4.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "16"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
