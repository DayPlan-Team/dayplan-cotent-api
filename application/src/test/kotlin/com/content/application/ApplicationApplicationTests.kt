package com.content.application

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(classes = [ApplicationTestConfiguration::class])
class ApplicationApplicationTests {
    @Test
    fun contextLoads() {
    }
}
