package com.content.adapter

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(classes = [AdapterTestConfiguration::class])
class AdapterApplicationTests {

    @Test
    fun contextLoads() {
    }

}
