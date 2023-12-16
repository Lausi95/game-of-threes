package de.lausi95.gameofthrees.adapter.internal

import de.lausi95.gameofthrees.domain.game.RandomStartValueStrategy
import de.lausi95.gameofthrees.domain.game.StartValueStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class GameConfiguration {

  @Bean
  fun startValueStrategy(): StartValueStrategy = RandomStartValueStrategy()
}
