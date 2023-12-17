package de.lausi95.gameofthrees.adapter.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import de.lausi95.gameofthrees.domain.game.Game
import de.lausi95.gameofthrees.domain.game.GameStartedPublisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
private class GameStartedPublisherKafkaProducer(
  private val kafkaTemplate: KafkaTemplate<String, String>,
  private val objectMapper: ObjectMapper,
  @Value("\${topics.game-started}") private val gameStartedTopic: String
): GameStartedPublisher {

  override fun publishGameStarted(game: Game) {
    val message = objectMapper.writeValueAsString(game)
    kafkaTemplate.send(gameStartedTopic, message)
  }
}
