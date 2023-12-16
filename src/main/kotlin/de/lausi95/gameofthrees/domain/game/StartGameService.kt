package de.lausi95.gameofthrees.domain.game

import de.lausi95.gameofthrees.domain.player.PlayerRepository
import org.springframework.stereotype.Component

@Component
class StartGameService(
  private val startValueStrategy: StartValueStrategy,
  private val startGamePublisher: StartGamePublisher,
  private val playerRepository: PlayerRepository) {

  fun startGame(): Game {
    val player = playerRepository.getMe()
    val game = Game(startValueStrategy.generateStartValue(), player.playerId)
    startGamePublisher.publishGameStarted(game)
    return game
  }
}
