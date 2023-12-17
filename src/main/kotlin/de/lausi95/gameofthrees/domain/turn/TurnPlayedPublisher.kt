package de.lausi95.gameofthrees.domain.turn

interface TurnPlayedPublisher {

  fun publishTurnPlayed(turn: Turn)
}
