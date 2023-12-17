package de.lausi95.gameofthrees.domain.model.game

import de.lausi95.gameofthrees.domain.model.game.Game

/**
 * Represents an interface for publishing the start of a game.
 */
interface GameStartedPublisher {

  /**
   * Publishes the start of a game.
   *
   * @param game The started game.
   */
  fun publishGameStarted(game: Game)
}
