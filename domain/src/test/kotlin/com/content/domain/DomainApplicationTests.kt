package com.content.domain

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(classes = [DomainTestConfiguration::class])
class DomainApplicationTests {
    @Test
    fun contextLoads() {
    }
}
