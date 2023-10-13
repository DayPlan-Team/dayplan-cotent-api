package com.content.adapter.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.concurrent.TimeUnit

@Profile("default | local")
@Configuration
class GrpcLocalConfig {

    /* 운영 배포시 address 설정 필요함 */
    @Bean
    @Qualifier("userManagedChannel")
    fun userManagedChannel(): ManagedChannel {
        return ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()
    }

    @Bean
    @Qualifier("contentManagedChannel")
    fun contentManagedChannel(): ManagedChannel {
        return ManagedChannelBuilder.forAddress("localhost", 50052)
            .usePlaintext()
            .build()
    }

    @PreDestroy
    fun destroy() {
        userManagedChannel().shutdown().awaitTermination(5, TimeUnit.SECONDS)
        contentManagedChannel().shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}