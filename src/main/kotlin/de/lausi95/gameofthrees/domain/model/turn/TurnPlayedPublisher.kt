package de.lausi95.gameofthrees.domain.model.turn

import de.lausi95.gameofthrees.domain.model.turn.Turn

interface TurnPlayedPublisher {

  fun publishTurnPlayed(turn: Turn)
}
