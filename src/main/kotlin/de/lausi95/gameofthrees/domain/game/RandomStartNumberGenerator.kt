package de.lausi95.gameofthrees.domain.game

import kotlin.random.Random

/**
 * Strategy to generate a random start value.
 *
 * @param maxValue The maximum value for the random start value (default is 10,000).
 *
 * @see StartNumberGenerator
 */
class RandomStartNumberGenerator(private val maxValue: Int = 10_000) : StartNumberGenerator {

  override fun generateStartValue(): Int = Random.nextInt(1, maxValue)
}
