package com.example.demo

import com.example.demo.domain.RatingManager
import com.example.demo.domain.ShowManager
import com.example.demo.domain.port.primary.RatingManagerPort
import com.example.demo.domain.port.primary.ShowManagerPort
import com.example.demo.domain.port.secondary.RatingPersistencePort
import com.example.demo.domain.port.secondary.ShowPersistencePort
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class DemoApplication(private val showPersistencePort: ShowPersistencePort, private val ratingPersistencePort: RatingPersistencePort) {
	@Bean
	open fun showManager(): ShowManagerPort = ShowManager(showPersistencePort, ratingPersistencePort)
	@Bean
	open fun ratingManager(): RatingManagerPort = RatingManager(ratingPersistencePort, showPersistencePort)
}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}



