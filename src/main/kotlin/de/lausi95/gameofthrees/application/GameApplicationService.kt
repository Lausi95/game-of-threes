package de.lausi95.gameofthrees.application

import de.lausi95.gameofthrees.domain.game.Game
import de.lausi95.gameofthrees.domain.game.StartGameService
import org.springframework.stereotype.Service

@Service
class GameApplicationService(private val startGameService: StartGameService) {

  fun startGame(): Game {
    return startGameService.startGame()
  }
}
