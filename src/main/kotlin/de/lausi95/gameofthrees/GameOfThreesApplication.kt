package de.lausi95.gameofthrees

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GameOfThreesApplication

fun main(args: Array<String>) {
  runApplication<GameOfThreesApplication>(*args)
}
