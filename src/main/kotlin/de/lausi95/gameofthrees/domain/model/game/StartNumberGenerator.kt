package de.lausi95.gameofthrees.domain.model.game

import kotlin.random.Random

/**
 * Represents a strategy for generating the start value of a game.
 */
interface StartNumberGenerator {

  /**
   * Generates the start value for a game.
   *
   * @return The generated start value.
   */
  fun generateStartValue(): Int
}

/**
 * Strategy to generate a random start value.
 *
 * @param maxValue The maximum value for the random start value (default is 10,000).
 *
 * @see StartNumberGenerator
 */
class RandomStartNumberGenerator(private val maxValue: Int = 10_000) : StartNumberGenerator {

  override fun generateStartValue(): Int = Random.nextInt(2, maxValue)
}
