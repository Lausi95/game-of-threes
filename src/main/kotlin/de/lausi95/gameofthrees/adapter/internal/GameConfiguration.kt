package de.lausi95.gameofthrees.adapter.internal

import de.lausi95.gameofthrees.domain.model.game.RandomFirstNumberGenerator
import de.lausi95.gameofthrees.domain.model.game.FirstNumberGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class GameConfiguration {

  @Bean
  fun startValueStrategy(): FirstNumberGenerator = RandomFirstNumberGenerator()
}
