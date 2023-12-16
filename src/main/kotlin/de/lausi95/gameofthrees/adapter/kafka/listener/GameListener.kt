package de.lausi95.gameofthrees.adapter.kafka.listener

import com.fasterxml.jackson.databind.ObjectMapper
import de.lausi95.gameofthrees.domain.game.Game
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
private class GameListener(private val objectMapper: ObjectMapper) {

  private val log = LoggerFactory.getLogger(this.javaClass)

  @KafkaListener(topics = ["\${topics.game-started}"], concurrency = "2")
  fun startGameListener(message: String) {
    val game = objectMapper.readValue(message, Game::class.java)
    log.info("Game Started: Player: ${game.playerId}, StartValue: ${game.startValue}")
  }
}
