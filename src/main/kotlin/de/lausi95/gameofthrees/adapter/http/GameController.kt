package de.lausi95.gameofthrees.adapter.http

import de.lausi95.gameofthrees.application.GameApplicationService
import de.lausi95.gameofthrees.domain.model.game.Game
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/games")
private class GameController(private val gameApplicationService: GameApplicationService) {

  @PostMapping
  fun startGame(): Game? = gameApplicationService.startGame()
}
