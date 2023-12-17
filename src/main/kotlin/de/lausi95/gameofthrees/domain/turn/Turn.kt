package de.lausi95.gameofthrees.domain.turn

import de.lausi95.gameofthrees.domain.game.Game
import de.lausi95.gameofthrees.domain.player.Player
import org.slf4j.LoggerFactory

fun Int.isValid(): Boolean = VALID_MOVES.contains(this)

fun Int.distanceToPrevNumberDivisibleBy(): Int = (this / DIVISOR) * DIVISOR - this
fun Int.distanceToNextNumberDivisibleBy(): Int = (this / DIVISOR + 1) * DIVISOR - this

fun Int.resolveResponseNumber(move: Int): Int = (this + move) / DIVISOR

fun Int.resolveStartingNumber(move: Int): Int = this * DIVISOR - move

typealias PublishTurnFunction = (Turn) -> Unit

internal fun Int.resolveNextMove(): Int {
  return listOf(
    this.distanceToNextNumberDivisibleBy(),
    this.distanceToPrevNumberDivisibleBy()
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
    fun playFirstTurn(player: Player, game: Game, moveResolver: MoveResolver = AUTOMATIC_MOVE_RESOLVER, onValidTurn: PublishTurnFunction) {
      if (player.playerId == game.initiatorPlayerId) {
        return
      }

      log.info("I (${player.playerId}) Playing the Game against ${game.initiatorPlayerId} with starting number: ${game.startNumber}")

      val firstMove = moveResolver.decideMove(game.startNumber)
      val responseNumber = game.startNumber.resolveResponseNumber(firstMove)
      onValidTurn(Turn(player.playerId, game.initiatorPlayerId, game.startNumber, firstMove, responseNumber))
    }
  }

  init {
    if (!move.isValid())
      throw IllegalArgumentException("Invalid move. Valid Moves: $VALID_MOVES")
    if (responseNumber.resolveStartingNumber(move) != startingNumber)
      throw IllegalArgumentException("Impossible move")
  }

  /**
   * Plays the next turn in the game.
   *
   * @param onNextTurn a function that will be called with the next turn as a parameter
   */
  fun playNextTurn(me: Player, moveResolver: MoveResolver = AUTOMATIC_MOVE_RESOLVER, onNextTurn: PublishTurnFunction) {
    if (opponentPlayerId == playerId) {
      return
    }
    if (opponentPlayerId == me.playerId) {
      return
    }

    val move = moveResolver.decideMove(responseNumber)

    val nextTurn = Turn(
      opponentPlayerId,
      playerId,
      responseNumber,
      move,
      responseNumber.resolveResponseNumber(move)
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
