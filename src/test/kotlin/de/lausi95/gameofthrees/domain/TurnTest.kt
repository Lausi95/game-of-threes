package de.lausi95.gameofthrees.domain

import de.lausi95.gameofthrees.domain.game.Game
import de.lausi95.gameofthrees.domain.player.Player
import de.lausi95.gameofthrees.domain.turn.Turn
import de.lausi95.gameofthrees.someInt
import de.lausi95.gameofthrees.somePlayer
import de.lausi95.gameofthrees.someString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

class TurnTest {

  @Test
  fun nextTurn_returnValidNextTurn() {
    val me = somePlayer()
    val somePlayerId = someString()
    val someOpponentId = someString()

    val turn = Turn(somePlayerId, someOpponentId, 18, 0, 6)
    var called = false
    turn.playNextTurn(me) {
      assertEquals(Turn(someOpponentId, somePlayerId, 6, 0, 2), it)
      called = true
    }
    assertTrue(called)
  }

  @RepeatedTest(10)
  fun isWinningMove_true_onWinningResponseValue() {
    val me = somePlayer()
    val somePlayerId = someString()
    val someOpponentId = someString()

    val move = someInt(-1, 2)

    val someMoveThatWinsNextTurn = Turn(somePlayerId, someOpponentId, 3 * (3 - move) - move, move, 3 - move)

    var onNextTurnTriggered = false
    someMoveThatWinsNextTurn.playNextTurn(me) {
      onNextTurnTriggered = true
    }
    assertFalse(onNextTurnTriggered)
  }

  @RepeatedTest(10)
  fun isWinningMove_false_onNotWinningResponseValue() {
    val somePlayerId = someString()
    val someOpponentId = someString()

    val someNotWinningResult = 2 + someInt()
    val turn = Turn(somePlayerId, someOpponentId, someNotWinningResult * 3, 0, someNotWinningResult)

    assertFalse(turn.isWinningTurn())
  }

  @RepeatedTest(10)
  fun init_cannotCreateTurn_whenTurnIsInvalid() {
    assertThrows(IllegalArgumentException::class.java) {
      val somePlayerId = someString()
      val someOpponentId = someString()

      val someWrongMove = someInt(from = 2, to = 10)
      val someStartingNumber = 18 - someWrongMove
      Turn(somePlayerId, someOpponentId, someStartingNumber, someWrongMove, someStartingNumber / 3)
    }
  }

  @RepeatedTest(10)
  fun init_cannotCreateTurn_whenStartingNumberPlugMoveIsNotDivisibleByThree() {
    assertThrows(IllegalArgumentException::class.java) {
      val somePlayerId = someString()
      val someOpponentId = someString()

      val someResult = someInt(from = 1)
      val someStartingNumber = someResult * 3
      val move = 1

      Turn(somePlayerId, someOpponentId, someStartingNumber, move, someResult)
    }
  }

  @RepeatedTest(10)
  fun init_canConstructTurn() = assertDoesNotThrow {
    val somePlayerId = someString()
    val someOpponentId = someString()

    val someResult = someInt(from = 1)
    val someMove = someInt(from = 1, to = 2)
    val expectedInput = (someResult * 3) - someMove

    Turn(somePlayerId, someOpponentId, expectedInput, someMove, someResult)
  }

  @RepeatedTest(30)
  fun init_justWithStartingNumber_determinesValuesCorrectly_withRandomInput() {
    val someMove = someInt(from = -1, to = 2)
    val someStartNumber = someInt(from = 1) * 3 - someMove

    val somePlayer = Player(someString())
    val someGame = Game(someStartNumber, someString())

    var onValidTurnTriggered = false
    Turn.playFirstTurn(somePlayer, someGame) {
      onValidTurnTriggered = true
      assertEquals(Turn(somePlayer.playerId, someGame.initiatorPlayerId, someStartNumber, someMove, (someStartNumber + someMove) / 3), it)
    }
    assertTrue(onValidTurnTriggered)
  }
}
