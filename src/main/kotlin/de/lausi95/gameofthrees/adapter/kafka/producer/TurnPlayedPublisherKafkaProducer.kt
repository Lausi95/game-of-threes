package de.lausi95.gameofthrees.adapter.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import de.lausi95.gameofthrees.adapter.kafka.dto.TurnDto
import de.lausi95.gameofthrees.domain.model.turn.TurnPlayedPublisher
import de.lausi95.gameofthrees.domain.model.turn.Turn
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
private class TurnPlayedPublisherKafkaProducer(
  private val kafkaTemplate: KafkaTemplate<String, String>,
  private val objectMapper: ObjectMapper,
  @Value("\${topics.turn-played}") private val turnPlayedTopic: String
) : TurnPlayedPublisher {

  override fun publishTurnPlayed(turn: Turn) {
    val turnDto = TurnDto(turn.playerId, turn.opponentPlayerId, turn.startingNumber, turn.move, turn.responseNumber)
    val message = objectMapper.writeValueAsString(turnDto)
    kafkaTemplate.send(turnPlayedTopic, message)
  }
}
