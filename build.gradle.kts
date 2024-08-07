import com.google.protobuf.gradle.GenerateProtoTask
import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.google.protobuf") version "0.9.4"
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    // GRPC
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("com.google.protobuf:protobuf-java:3.24.3")
    implementation("io.grpc:grpc-netty-shaded:1.58.0")
    implementation("io.grpc:grpc-protobuf:1.58.0")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.bootJar {
    mainClass = "com.content.api.ApiApplicationKt"
}

allprojects {

    group = "com.dayplancontent"
    version = "0.0.1-SNAPSHOT"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        apply(plugin = "org.springframework.boot")
        apply(plugin = "io.spring.dependency-management")
        apply(plugin = "org.jetbrains.kotlin.plugin.spring")
        apply(plugin = "org.jlleitschuh.gradle.ktlint")
        apply(plugin = "kotlin")
        apply(plugin = "kotlin-kapt")
        apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    }

    ktlint {
        filter {
            exclude("**/generated/**")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        implementation("com.h2database:h2")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.5.2")

        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
        testImplementation("io.kotest:kotest-assertions-core:5.4.2")
        testImplementation("io.kotest:kotest-property:5.4.2")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
        testImplementation("io.mockk:mockk:1.13.7")
        implementation(kotlin("script-runtime"))
    }

    tasks.register("prepareKotlinBuildScriptModel") {}
}

dependencies {
    implementation(project(":util"))
    implementation(project(":domain"))
    implementation(project(":application"))
    implementation(project(":adapter"))
    implementation(project(":api"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.58.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                add(GenerateProtoTask.PluginOptions("grpc"))
                add(GenerateProtoTask.PluginOptions("grpckt"))
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("content/adapter/src/main/proto")
        }
        java {
            srcDirs("content/adapter/build/generated/source/proto/main/grpc")
            srcDirs("content/adapter/build/generated/source/proto/main/java")
        }
    }
}

ktlint {
    filter {
        exclude { element ->
            element.file.path.contains("generated")
        }
    }
}
