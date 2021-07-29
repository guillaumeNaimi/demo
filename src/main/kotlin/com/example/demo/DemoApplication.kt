package com.example.demo

import com.example.demo.domain.ShowManager
import com.example.demo.domain.port.primary.ShowManagerPort
import com.example.demo.domain.port.secondary.ShowPersistencePort
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DemoApplication(private val showPersistencePort: ShowPersistencePort) {
	@Bean
	fun showManager(): ShowManagerPort = ShowManager(showPersistencePort)
}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}



