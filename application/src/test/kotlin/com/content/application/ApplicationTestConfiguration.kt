package com.content.application

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootConfiguration
@SpringBootApplication
@ComponentScan(basePackages = ["com.content.**"])
class ApplicationTestConfiguration