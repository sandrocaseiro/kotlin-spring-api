import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.*

group = "com.sandrocaseiro"
version = "1.0.0"
description = "Kotlin Spring Template API"
java.sourceCompatibility = JavaVersion.VERSION_1_8

plugins {
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.owasp.dependencycheck") version "5.3.0"
    id("org.sonarqube") version "2.8"
    id("org.liquibase.gradle") version "2.0.2"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    war
    jacoco
}

project.ext {
    set("env", "dev")
}

sourceSets {
    val main by getting
    val test by getting
    val integrationTest by creating {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("src/integration-test/kotlin")
        }
        resources.srcDir(file("src/integration-test/resources"))
        compileClasspath += main.compileClasspath + test.compileClasspath
        runtimeClasspath += main.runtimeClasspath + test.runtimeClasspath
    }
}

val developmentOnly: Configuration by configurations.creating
configurations {
    val testCompile by getting
    val testRuntime by getting
    val integrationTestCompile by getting {
        extendsFrom(testCompile)
    }
    val integrationTestRuntime by getting {
        extendsFrom(testRuntime)
    }
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
}

val beanIOVersion = "2.1.0"
val cucumberVersion = "5.5.0"
val feignVersion = "10.7.4"
val guavaVersion = "28.2-jre"
val hibernateValidationVersion = "5.4.3.Final"
val jwtVersion = "0.9.1"
val liquibaseVersion = "3.8.7"
val restAssuredVersion = "4.2.0"
val springCloudVersion = "Hoxton.SR3"
val swaggerVersion = "2.9.2"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

dependencies {
    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    //Needed for Wildfly 9
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.hibernate.validator", "hibernate-validator")
        exclude("jakarta.validation", "jakarta.validation-api")
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }

    if (gradle.startParameter.taskNames.any { listOf(tasks.bootJar.name, tasks.bootWar.name, tasks.bootRun.name).contains(it) })
        providedRuntime("org.springframework.boot:spring-boot-starter-undertow")

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("org.hibernate", "hibernate-validator", hibernateValidationVersion)

    implementation("io.github.openfeign", "feign-jackson", feignVersion)
    implementation("io.github.openfeign", "feign-okhttp", feignVersion)

    implementation("io.springfox", "springfox-swagger2", swaggerVersion)
    implementation("io.springfox", "springfox-swagger-ui", swaggerVersion)

    implementation("io.jsonwebtoken", "jjwt", jwtVersion)

    implementation("org.beanio", "beanio", beanIOVersion)

    //Check
    implementation("org.liquibase", "liquibase-core", liquibaseVersion)
    liquibaseRuntime("org.liquibase", "liquibase-core", liquibaseVersion)
    liquibaseRuntime("ch.qos.logback", "logback-core", "1.2.3")
    liquibaseRuntime("ch.qos.logback","logback-classic", "1.2.3")
    liquibaseRuntime("com.oracle.ojdbc", "ojdbc8")

    runtimeOnly("com.oracle.ojdbc", "ojdbc8")

    implementation("com.google.guava", "guava", guavaVersion)

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("com.h2database", "h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.cucumber", "cucumber-java8", cucumberVersion)
    testImplementation("io.cucumber", "cucumber-junit", cucumberVersion)
    testImplementation("io.cucumber", "cucumber-spring", cucumberVersion)
    testImplementation("io.rest-assured", "rest-assured", restAssuredVersion) {
        exclude("io.rest-assured", "json-path")
        exclude("io.rest-assured", "xml-path")
    }
    testImplementation("io.rest-assured", "json-path", restAssuredVersion)
    testImplementation("io.rest-assured", "xml-path", restAssuredVersion)
}

dependencyCheck {
    format = org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL
    failOnError = false
    failBuildOnCVSS = 11F
    junitFailOnCVSS = 11F
}

liquibase {
    val props = Properties()
    val inputStream = FileInputStream("src/main/resources/liquibase.properties")
    props.load(inputStream)

    activities.register("main") {
        arguments = mapOf(
            "url" to props["url"],
            "username" to  props["username"],
            "password" to  props["password"],
            "changeLogFile" to  props["changeLogFile"]
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

listOf("dev", "prod").forEach { env ->
    tasks.register("bootJar${env.capitalize()}") {
        ext["env"] = env

        finalizedBy(tasks.bootJar)
    }

    tasks.register("bootWar${env.capitalize()}") {
        ext["env"] = env

        finalizedBy(tasks.bootWar)
    }
}

tasks.processResources {
    finalizedBy(tasks.named("yaml"))
}

tasks.register<Test>("integration-test") {
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
}

tasks.register<Copy>("yaml") {
    from("src/main/resources") {
        include("application.yml")
    }
    into("$buildDir/resources/main")

    expand(
        "activeProfile" to ext.get("env"),
        "appName" to project.name,
        "appDescription" to project.description,
        "appVersion" to project.version
    )
}
