import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {

    /* API */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-java8:2.9.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("com.ninja-squad:springmockk:4.0.2")

    implementation(project(":util"))
    implementation(project(":domain"))
    implementation(project(":adapter"))
    implementation(project(":application"))
}

sourceSets {
    main {
        java.srcDir("src/main/generated")
    }
}

kapt {
    arguments {
        arg("querydsl.generated", "src/main/generated")
    }
}