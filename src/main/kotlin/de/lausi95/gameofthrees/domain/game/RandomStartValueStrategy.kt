package de.lausi95.gameofthrees.domain.game

import kotlin.random.Random

class RandomStartValueStrategy(private val maxValue: Int = 10_000) : StartValueStrategy {

  override fun generateStartValue(): Int = Random.nextInt(1, maxValue)
}
