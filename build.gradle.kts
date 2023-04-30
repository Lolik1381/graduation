import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.liquibase.gradle") version "2.0.3"
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("kapt") version "1.3.72"
    kotlin("plugin.allopen") version "1.4.32"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

group = "ru.stankin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    kapt("org.mapstruct:mapstruct-processor:1.4.1.Final")
    kapt("org.hibernate:hibernate-jpamodelgen:5.4.30.Final")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-quartz:2.4.1")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security:2.7.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.liquibase:liquibase-core")
    implementation("com.vladmihalcea:hibernate-types-52:2.19.2")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("org.mapstruct:mapstruct:1.4.1.Final")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("net.javacrumbs.shedlock:shedlock-spring:2.2.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:2.1.0")
    implementation("software.amazon.awssdk:s3:2.17.32")
    implementation("commons-io:commons-io:2.11.0")

    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    liquibaseRuntime("ch.qos.logback:logback-classic")
    liquibaseRuntime("ch.qos.logback:logback-core")
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.postgresql:postgresql")
    liquibaseRuntime("org.yaml:snakeyaml")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "changeLogFile" to "src/main/resources/db/changelog/db.changelog-master.yaml",
            "url" to "jdbc:postgresql://localhost:5432/postgres",
            "username" to "postgres",
            "password" to "postgres"
        )
    }
}