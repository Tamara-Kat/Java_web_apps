plugins {
    id("java")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.3.232")

    implementation("org.flywaydb:flyway-core:11.19.0")

    implementation("org.hibernate.orm:hibernate-core:6.6.4.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    implementation("org.slf4j:slf4j-simple:2.0.16")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}