package de.lausi95.gameofthrees.adapter.internal

import de.lausi95.gameofthrees.domain.game.RandomStartNumberGenerator
import de.lausi95.gameofthrees.domain.game.StartNumberGenerator
import de.lausi95.gameofthrees.domain.turn.AUTOMATIC_MOVE_RESOLVER
import de.lausi95.gameofthrees.domain.turn.MANUAL_MOVE_RESOLVER
import de.lausi95.gameofthrees.domain.turn.MoveResolver
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class GameConfiguration {

  private val log = LoggerFactory.getLogger(this.javaClass)

  @Bean
  fun startValueStrategy(): StartNumberGenerator = RandomStartNumberGenerator()

  @Bean
  @ConditionalOnExpression("'\${game-of-three.move-resolver}'.equals('MANUAL')")
  fun manualMoveResolver(): MoveResolver {
    log.info("Using manual move resolver.")
    return MANUAL_MOVE_RESOLVER
  }

  @Bean
  @ConditionalOnMissingBean
  fun autoMoveResolver(): MoveResolver {
    log.info("Using automatic move resolver.")
    return AUTOMATIC_MOVE_RESOLVER
  }
}
