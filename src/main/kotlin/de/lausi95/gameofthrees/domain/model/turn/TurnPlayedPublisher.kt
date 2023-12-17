package de.lausi95.gameofthrees.domain.model.turn

interface TurnPlayedPublisher {

  fun publishTurnPlayed(turn: Turn)
}
