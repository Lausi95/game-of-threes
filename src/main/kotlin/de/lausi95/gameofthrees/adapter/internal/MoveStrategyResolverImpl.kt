package de.lausi95.gameofthrees.adapter.internal

import de.lausi95.gameofthrees.domain.model.turn.MoveStrategy
import de.lausi95.gameofthrees.domain.model.turn.MoveStrategyResolver
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
private class MoveStrategyResolverImpl(
  @Value("\${game-of-three.move-resolver}") val moveStrategyName: String,
  val moveStrategies: List<MoveStrategy>
): MoveStrategyResolver {

  override fun getMoveStrategy(): MoveStrategy {
    return moveStrategies.find { moveStrategyName == it.name() }
      ?: throw IllegalStateException("Cannot find MoveStrategy with name $moveStrategyName")
  }
}
