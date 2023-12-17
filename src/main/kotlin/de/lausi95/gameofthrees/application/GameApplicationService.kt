package de.lausi95.gameofthrees.application

import de.lausi95.gameofthrees.domain.model.game.Game
import de.lausi95.gameofthrees.domain.model.game.GameStartedPublisher
import de.lausi95.gameofthrees.domain.model.game.StartNumberGenerator
import de.lausi95.gameofthrees.domain.model.player.PlayerRepository
import org.springframework.stereotype.Service

@Service
class GameApplicationService(
  private val startNumberGenerator: StartNumberGenerator,
  private val gameStartedPublisher: GameStartedPublisher,
  private val playerRepository: PlayerRepository,
) {

  /**
   * Starts a game.
   *
   * @return The initialized game instance if the game is properly setup, otherwise `null`
   */
  fun startGame(): Game? = Game.start(playerRepository.getMe(), startNumberGenerator, gameStartedPublisher)
}
