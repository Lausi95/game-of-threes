package de.lausi95.gameofthrees.adapter.kafka.listener

import com.fasterxml.jackson.databind.ObjectMapper
import de.lausi95.gameofthrees.adapter.kafka.dto.TurnDto
import de.lausi95.gameofthrees.application.TurnApplicationService
import de.lausi95.gameofthrees.domain.turn.Turn
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
private class TurnListener(
  private val objectMapper: ObjectMapper,
  private val turnApplicationService: TurnApplicationService
) {

  @KafkaListener(topics = ["\${topics.turn-played}"])
  fun turnPlayed(message: String) {
    val turnDto = objectMapper.readValue(message, TurnDto::class.java)
    val turn = Turn(turnDto.playerId, turnDto.opponentPlayerId, turnDto.startingNumber, turnDto.move, turnDto.responseNumber)
    turnApplicationService.playNextTurn(turn)
  }
}
