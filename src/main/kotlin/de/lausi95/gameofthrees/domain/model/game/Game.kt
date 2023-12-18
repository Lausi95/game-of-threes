package de.lausi95.gameofthrees.domain.model.game

import de.lausi95.gameofthrees.domain.model.player.Player
import org.slf4j.LoggerFactory

/**
 * Represents a game.
 */
data class Game(
  val firstNumber: Int,
  val initiatorPlayerId: String,
) {

  companion object {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Starts a new game.
     *
     * @param me The player who initiates the game.
     * @param firstNumberGenerator The strategy for generating the start value of the game.
     * @param gameStartedPublisher The function for publishing the game.
     * @return The initialized game instance if the game is properly setup, otherwise `null`.
     */
    fun start(me: Player, firstNumberGenerator: FirstNumberGenerator, gameStartedPublisher: GameStartedPublisher): Game? {
      val startValue = firstNumberGenerator.generateFirstNumber()
      if (startValue <= 1) {
        log.warn("Won't start game with start value $startValue. It has to be > 1 to create a valid game setup.")
        return null
      }

      log.info("I (${me.playerId}) challenging players to play against me on number ${startValue}!")

      val game = Game(startValue, me.playerId)
      gameStartedPublisher.publishGameStarted(game)
      return game
    }
  }
}
