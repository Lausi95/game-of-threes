package de.lausi95.gameofthrees.domain.turn

import de.lausi95.gameofthrees.domain.game.Game
import de.lausi95.gameofthrees.domain.player.Player
import org.slf4j.LoggerFactory

fun Int.isValid(): Boolean = VALID_MOVES.contains(this)

fun Int.distanceToPrevNumberDivisibleBy(divisor: Int): Int = (this / divisor) * divisor - this
fun Int.distanceToNextNumberDivisibleBy(divisor: Int): Int = (this / divisor + 1) * divisor - this

fun Int.resolveResponseNumber(divisor: Int, move: Int): Int = (this + move) / divisor
fun Int.resolveResponseNumber(divisor: Int): Int = this.resolveResponseNumber(divisor, this.resolveNextMove(divisor))

fun Int.resolveStartingNumber(divisor: Int, move: Int): Int = this * divisor - move

typealias PublishTurnFunction = (Turn) -> Unit

private fun Int.resolveNextMove(divisor: Int): Int {
  return listOf(
    this.distanceToNextNumberDivisibleBy(divisor),
    this.distanceToPrevNumberDivisibleBy(divisor)
  ).filter { VALID_MOVES.contains(it) }.min()
}

const val DIVISOR = 3

val VALID_MOVES = listOf(-1, 0, 1)

/**
 * Represents a turn in a game.
 *
 * @property playerId The ID of the player making the turn.
 * @property opponentPlayerId The ID of the opponent player.
 * @property startingNumber The starting number for the turn.
 * @property move The move made by the player.
 * @property responseNumber The number for the opponent player to play the next move on.
 */
data class Turn(
  val playerId: String,
  val opponentPlayerId: String,
  val startingNumber: Int,
  val move: Int,
  val responseNumber: Int
) {

  companion object {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Plays the first turn of the game.
     *
     * @param player The player playing the turn.
     * @param game The game being played.
     * @param onValidTurn A function that will be called with the valid turn as a parameter.
     */
    fun playFirstTurn(player: Player, game: Game, onValidTurn: PublishTurnFunction) {
      if (player.playerId == game.initiatorPlayerId) {
        return
      }

      log.info("I (${player.playerId}) Playing the Game against ${game.initiatorPlayerId} with starting number: ${game.startNumber}")
      onValidTurn(Turn(player.playerId, game.initiatorPlayerId, game.startNumber))
    }
  }

  init {
    if (!move.isValid())
      throw IllegalArgumentException("Invalid move. Valid Moves: $VALID_MOVES")
    if (responseNumber.resolveStartingNumber(DIVISOR, move) != startingNumber)
      throw IllegalArgumentException("Impossible move")
  }

  constructor(
    playerId: String,
    opponentPlayerId: String,
    startingNumber: Int
  ) : this(
    playerId,
    opponentPlayerId,
    startingNumber,
    startingNumber.resolveNextMove(DIVISOR),
    startingNumber.resolveResponseNumber(DIVISOR)
  )

  /**
   * Plays the next turn in the game.
   *
   * @param onNextTurn a function that will be called with the next turn as a parameter
   */
  fun playNextTurn(onNextTurn: PublishTurnFunction) {
    if (opponentPlayerId == playerId) {
      return
    }

    val nextTurn = Turn(
      opponentPlayerId,
      playerId,
      responseNumber,
      responseNumber.resolveNextMove(DIVISOR),
      responseNumber.resolveResponseNumber(DIVISOR)
    )

    if (nextTurn.isWinningTurn()) {
      log.info("I WON!! Against player $opponentPlayerId on the move $move on the number $startingNumber")
      return
    }

    log.info("I ($playerId) playing the move $move against the player $opponentPlayerId on number $startingNumber. Number for opponent: $responseNumber")

    onNextTurn(nextTurn)
  }

  /**
   * Determines if the current turn is a winning turn.
   *
   * @return true if the response number is 1, indicating a winning turn. Otherwise, false.
   */
  fun isWinningTurn(): Boolean = responseNumber == 1
}
