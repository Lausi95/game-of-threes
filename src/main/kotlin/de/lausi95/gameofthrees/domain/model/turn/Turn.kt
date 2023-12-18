package de.lausi95.gameofthrees.domain.model.turn

import de.lausi95.gameofthrees.domain.model.game.Game
import de.lausi95.gameofthrees.domain.model.player.Player
import org.slf4j.LoggerFactory

fun Int.isValid(): Boolean = VALID_MOVES.contains(this)

fun Int.distanceToPrevNumberDivisibleBy(): Int = (this / DIVISOR) * DIVISOR - this
fun Int.distanceToNextNumberDivisibleBy(): Int = (this / DIVISOR + 1) * DIVISOR - this

fun Int.resolveNextNumber(move: Int): Int = (this + move) / DIVISOR

fun Int.resolveStartingNumber(move: Int): Int = this * DIVISOR - move

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
 * @property givenNumber The starting number for the turn.
 * @property move The move made by the player.
 * @property numberForOpponent The number for the opponent player to play the next move on.
 */
data class Turn(
  val playerId: String,
  val opponentPlayerId: String,
  val givenNumber: Int,
  val move: Int,
  val numberForOpponent: Int
) {

  companion object {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Plays the first turn of a game.
     *
     * @param me The player who is playing the turn.
     * @param game The game in which the turn is being played.
     * @param moveResolver The move resolver for resolving the player's move.
     * @param turnPlayedPublisher The publisher for publishing the turn.
     */
    fun playFirstTurn(me: Player, game: Game, moveResolver: MoveResolver, turnPlayedPublisher: TurnPlayedPublisher) {
      if (me.playerId == game.initiatorPlayerId) {
        return
      }

      val firstMove = moveResolver.resolveMove(game.firstNumber)
      val numberForOpponent = game.firstNumber.resolveNextNumber(firstMove)

      val firstTurn = Turn(me.playerId, game.initiatorPlayerId, game.firstNumber, firstMove, numberForOpponent)
      log.info("I (${me.playerId}) Playing the Game against player '${game.initiatorPlayerId}' with starting number: ${game.firstNumber}. First move: ${firstTurn.formatMove()}")
      turnPlayedPublisher.publishTurnPlayed(firstTurn)
    }
  }

  init {
    if (!move.isValid())
      throw IllegalArgumentException("Invalid move. Valid Moves: $VALID_MOVES")
    if (numberForOpponent.resolveStartingNumber(move) != givenNumber)
      throw IllegalArgumentException("Impossible move")
  }

  /**
   * Plays the next turn in the game.
   *
   * @param me The current player.
   * @param moveResolver The move resolver for resolving the player's move.
   * @param turnPlayedPublisher The publisher for publishing the turn.
   */
  fun playNextTurn(me: Player, moveResolver: MoveResolver, turnPlayedPublisher: TurnPlayedPublisher) {
    if (me.playerId != opponentPlayerId || me.playerId == playerId) return
    val myOpponent = playerId

    val myGivenNumber = numberForOpponent
    val myMove = moveResolver.resolveMove(myGivenNumber)
    val myNumberForOpponent = myGivenNumber.resolveNextNumber(myMove)

    val myTurn = Turn(
      me.playerId,
      myOpponent,
      myGivenNumber,
      myMove,
      myNumberForOpponent
    )

    if (myTurn.isWinningTurn()) {
      log.info("I (${me.playerId}) WON!! Against player '$playerId' on the move: ${myTurn.formatMove()}")
      return
    }

    log.info("I (${me.playerId}) playing next move against the player '$myOpponent' on number $myGivenNumber. Move: ${myTurn.formatMove()}")

    turnPlayedPublisher.publishTurnPlayed(myTurn)
  }

  fun formatMove(): String {
    return "($givenNumber + ($move)) / 3 = $numberForOpponent"
  }

  /**
   * Determines if the current turn is a winning turn.
   *
   * @return true if the response number is 1, indicating a winning turn. Otherwise, false.
   */
  fun isWinningTurn(): Boolean = numberForOpponent == 1
}
