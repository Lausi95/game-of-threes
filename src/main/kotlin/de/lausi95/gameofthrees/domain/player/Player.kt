package de.lausi95.gameofthrees.domain.player

/**
 * Represents a player in the game.
 */
data class Player(val playerId: String)

/**
 * Represents a repository for managing player data.
 */
interface PlayerRepository {

  /**
   * Retrieves the player object representing the current user.
   *
   * @return The player object representing the current user.
   */
  fun getMe(): Player
}
