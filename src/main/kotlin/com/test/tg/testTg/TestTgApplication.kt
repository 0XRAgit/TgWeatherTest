package com.test.tg.testTg

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestTgApplication

fun main(args: Array<String>) {
	println("Start Bot")
	runApplication<TestTgApplication>(*args)
	println("Bot Started")
}
