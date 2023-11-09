import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// kotlin ver - 1.6.10
val springBootVer: String by project // 3.0.2
val springSecurityVer: String by project // 6.0.1
val kotlinVer: String by project // 1.8.0
val jacksonVer: String by project // 2.14.1
val jwtVer: String by project // 0.9.1
val jsonVer: String by project // 20220924
val jaxbVer: String by project // 2.3.1
val javaJwtVer: String by project // 4.2.2

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "ru.borntonight"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:$springBootVer")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springBootVer")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVer")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVer")
    implementation("org.springframework.boot:spring-boot-starter-websocket:$springBootVer")
    developmentOnly("org.springframework.boot:spring-boot-devtools:$springBootVer")
    testImplementation("org.springframework.security:spring-security-test:$springSecurityVer")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVer")

    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVer")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVer")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVer")
    implementation("io.jsonwebtoken:jjwt:$jwtVer")
    implementation("org.json:json:$jsonVer")

    implementation("com.auth0:java-jwt:$javaJwtVer")

    implementation("javax.xml.bind:jaxb-api:$jaxbVer")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
