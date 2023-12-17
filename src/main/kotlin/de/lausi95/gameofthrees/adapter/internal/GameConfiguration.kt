package de.lausi95.gameofthrees.adapter.internal

import de.lausi95.gameofthrees.domain.game.RandomStartNumberGenerator
import de.lausi95.gameofthrees.domain.game.StartNumberGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class GameConfiguration {

  @Bean
  fun startValueStrategy(): StartNumberGenerator = RandomStartNumberGenerator()
}
