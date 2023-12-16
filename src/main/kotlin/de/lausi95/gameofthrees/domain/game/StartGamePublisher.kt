package de.lausi95.gameofthrees.domain.game

interface StartGamePublisher {

  fun publishGameStarted(game: Game)
}
