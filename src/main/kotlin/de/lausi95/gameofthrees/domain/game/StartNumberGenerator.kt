package de.lausi95.gameofthrees.domain.game

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
