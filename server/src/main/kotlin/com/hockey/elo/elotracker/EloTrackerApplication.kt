package com.hockey.elo.elotracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EloTrackerApplication

fun main(args: Array<String>) {
	runApplication<EloTrackerApplication>(*args)
}
