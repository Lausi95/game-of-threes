package de.lausi95.gameofthrees.adapter.rest

import de.lausi95.gameofthrees.application.GameApplicationService
import de.lausi95.gameofthrees.domain.Turn
import de.lausi95.gameofthrees.domain.game.Game
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/games")
private class GameController(private val gameApplicationService: GameApplicationService) {

  @PostMapping
  fun startGame(): Game = gameApplicationService.startGame()
}
