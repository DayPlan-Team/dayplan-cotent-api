package com.content.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["com.content.adapter.**"])
@EntityScan(basePackages = ["com.content.adapter.**"])
@ComponentScan(basePackages = ["com.content.**"])
@SpringBootApplication
class ApplicationTestConfiguration