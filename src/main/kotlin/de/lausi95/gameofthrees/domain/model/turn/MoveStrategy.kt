package de.lausi95.gameofthrees.domain.model.turn

interface MoveStrategy {

  fun name(): String

  fun resolveMove(number: Int, moveCallback: (Int) -> Unit)
}
