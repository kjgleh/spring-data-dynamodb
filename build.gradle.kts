import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.noarg") version "1.4.21"
}

group = "me.kjgleh"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // kotlinFixture
    testImplementation("com.appmattus.fixture:fixture:1.1.0")

    // spring data dynamodb
    implementation("io.github.boostchicken:spring-data-dynamodb:5.2.5")

    // aws
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    // localstack
    implementation("org.testcontainers:localstack:1.15.2")
    testImplementation("org.testcontainers:junit-jupiter:1.15.2")
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

//noArg {
//    annotation("com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable")
//}