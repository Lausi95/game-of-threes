package de.lausi95.gameofthrees.domain.model.turn

interface MoveStrategyResolver {

  fun getMoveStrategy(): MoveStrategy
}
