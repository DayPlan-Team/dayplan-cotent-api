package com.content.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.content.**"])
@EnableJpaRepositories(basePackages = ["com.content.adapter.**"])
@EntityScan(basePackages = ["com.content.**"])
@ComponentScan(basePackages = ["com.content.**"])
class ApplicationTestConfiguration
