package de.lausi95.gameofthrees.adapter.kafka.listener

import com.fasterxml.jackson.databind.ObjectMapper
import de.lausi95.gameofthrees.adapter.kafka.dto.GameDto
import de.lausi95.gameofthrees.application.TurnApplicationService
import de.lausi95.gameofthrees.domain.game.Game
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
private class GameListener(
  private val objectMapper: ObjectMapper,
  private val turnApplicationService: TurnApplicationService,
) {

  @KafkaListener(topics = ["\${topics.game-started}"])
  fun startGameListener(message: String) {
    val gameDto = objectMapper.readValue(message, GameDto::class.java)
    val game = Game(gameDto.startNumber, gameDto.initiatorPlayerId)
    turnApplicationService.playFirstTurn(game)
  }
}
