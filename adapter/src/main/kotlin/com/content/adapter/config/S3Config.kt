package com.content.adapter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Profile("dev | prod")
@Configuration
class S3Config {

    @Bean
    fun applyS3Client(): S3Client {
        return S3Client.builder().region(Region.AP_NORTHEAST_2).build()
    }
}