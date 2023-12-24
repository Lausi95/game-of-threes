package de.lausi95.gameofthrees.domain.model.turn

import de.lausi95.gameofthrees.domain.model.game.Game
import de.lausi95.gameofthrees.domain.model.player.Player
import org.slf4j.LoggerFactory

val VALID_MOVES = listOf(-1, 0, 1)

fun Int.isValid(): Boolean = VALID_MOVES.contains(this)
fun Int.resolveNextNumber(move: Int): Int = (this + move) / 3
fun Int.resolveStartingNumber(move: Int): Int = this * 3 - move

/**
 * Represents a turn in a game.
 *
 * @property playerId The ID of the player making the turn.
 * @property opponentPlayerId The ID of the opponent player.
 * @property givenNumber The starting number for the turn.
 * @property move The move made by the player.
 * @property numberForOpponent The number for the opponent player to play the next move on.
 */
data class Turn (
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
     * @param moveStrategy The move resolver for resolving the player's move.
     * @param turnPlayedPublisher The publisher for publishing the turn.
     */
    fun playFirstTurn(me: Player, game: Game, moveStrategy: MoveStrategy, turnPlayedPublisher: TurnPlayedPublisher) {
      if (me.playerId == game.initiatorPlayerId) {
        return
      }

      moveStrategy.resolveMove(game.firstNumber) {
        val numberForOpponent = game.firstNumber.resolveNextNumber(it)

        val firstTurn = Turn(me.playerId, game.initiatorPlayerId, game.firstNumber, it, numberForOpponent)
        log.info("I (${me.playerId}) Playing the Game against player '${game.initiatorPlayerId}' with starting number: ${game.firstNumber}. First move: ${firstTurn.formatMove()}")
        turnPlayedPublisher.publishTurnPlayed(firstTurn)
      }
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
   * @param moveStrategy The move resolver for resolving the player's move.
   * @param turnPlayedPublisher The publisher for publishing the turn.
   */
  fun playNextTurn(me: Player, moveStrategy: MoveStrategy, turnPlayedPublisher: TurnPlayedPublisher) {
    if (me.playerId != opponentPlayerId || me.playerId == playerId) return
    val myOpponent = playerId

    val myGivenNumber = numberForOpponent
    moveStrategy.resolveMove(myGivenNumber) { myMove ->
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
        return@resolveMove
      }

      log.info("I (${me.playerId}) playing next move against the player '$myOpponent' on number $myGivenNumber. Move: ${myTurn.formatMove()}")

      turnPlayedPublisher.publishTurnPlayed(myTurn)
    }

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
