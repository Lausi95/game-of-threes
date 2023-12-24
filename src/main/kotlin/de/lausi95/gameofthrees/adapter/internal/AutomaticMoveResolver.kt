package de.lausi95.gameofthrees.adapter.internal

import de.lausi95.gameofthrees.domain.model.turn.MoveStrategy
import org.springframework.stereotype.Component

@Component
private class AutomaticMoveStrategy : MoveStrategy {

  override fun name(): String = "AUTOMATIC"

  override fun resolveMove(number: Int, moveCallback: (Int) -> Unit) {
    val mod = number % 3
    val move = (3 * mod * mod - 5 * mod) / 2
    moveCallback(move)
  }
}
