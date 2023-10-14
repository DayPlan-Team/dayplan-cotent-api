import org.springframework.boot.gradle.tasks.bundling.BootJar
import com.google.protobuf.gradle.*

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    id("com.google.protobuf") version "0.9.4"
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    /* RabbitMq */
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    /* MySQL */
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("mysql:mysql-connector-java:8.0.32")

    /* API */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-java8:2.9.0")

    /* GRPC */
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("com.google.protobuf:protobuf-java:3.24.3")
    implementation("io.grpc:grpc-netty-shaded:1.58.0")
    implementation("io.grpc:grpc-protobuf:1.58.0")
    implementation("net.devh:grpc-server-spring-boot-starter:2.15.0.RELEASE")

    implementation(project(":util"))
    implementation(project(":domain"))
    implementation(project(":application"))
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