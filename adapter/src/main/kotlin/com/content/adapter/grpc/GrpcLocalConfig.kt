package com.content.adapter.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class GrpcLocalConfig {

    @Value("\${grpc.content.server.port}")
    private lateinit var grpcContent: String

    @Value("\${grpc.user.server.port}")
    private lateinit var grpcUser: String

    /* 운영 배포시 address 설정 필요함 */
    @Bean
    @Qualifier("userManagedChannel")
    fun userManagedChannel(): ManagedChannel {
        return ManagedChannelBuilder.forAddress("localhost", grpcUser.toInt())
            .usePlaintext()
            .build()
    }

    @Bean
    @Qualifier("contentManagedChannel")
    fun contentManagedChannel(): ManagedChannel {
        return ManagedChannelBuilder.forAddress("localhost", grpcContent.toInt())
            .usePlaintext()
            .build()
    }

    @PreDestroy
    fun destroy() {
        userManagedChannel().shutdown().awaitTermination(5, TimeUnit.SECONDS)
        contentManagedChannel().shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}