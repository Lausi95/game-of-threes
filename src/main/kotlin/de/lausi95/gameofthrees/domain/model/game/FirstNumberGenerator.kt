package de.lausi95.gameofthrees.domain.model.game

import kotlin.random.Random

/**
 * Represents a strategy for generating the start value of a game.
 */
interface FirstNumberGenerator {

  /**
   * Generates the start value for a game.
   *
   * @return The generated start value.
   */
  fun generateFirstNumber(): Int
}

/**
 * Strategy to generate a random start value.
 *
 * @param maxValue The maximum value for the random start value (default is 10,000).
 *
 * @see FirstNumberGenerator
 */
class RandomFirstNumberGenerator(private val maxValue: Int = 1_000_000) : FirstNumberGenerator {

  override fun generateFirstNumber(): Int = Random.nextInt(2, maxValue)
}
