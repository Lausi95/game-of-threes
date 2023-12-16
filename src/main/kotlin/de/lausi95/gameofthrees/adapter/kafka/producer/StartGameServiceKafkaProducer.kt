package de.lausi95.gameofthrees.adapter.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import de.lausi95.gameofthrees.domain.game.Game
import de.lausi95.gameofthrees.domain.game.StartGamePublisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
private class StartGameServiceKafkaProducer(
  private val kafkaTemplate: KafkaTemplate<String, String>,
  private val objectMapper: ObjectMapper,
  @Value("\${topics.game-started}") private val gameStartedTopic: String
): StartGamePublisher {

  override fun publishGameStarted(game: Game) {
    val message = objectMapper.writeValueAsString(game)
    kafkaTemplate.send(gameStartedTopic, message)
  }
}
