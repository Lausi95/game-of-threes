package de.lausi95.gameofthrees.domain

import de.lausi95.gameofthrees.someInt
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

class TurnTest {

  @Test
  fun nextTurn_returnValidNextTurn1() {
    val turn = Turn(9, 0, 3)
    val nextTurn = turn.nextTurn()

    assertEquals(Turn(3, 0, 1), nextTurn)
  }

  @Test
  fun nextTurn_returnValidNextTurn2() {
    val turn = Turn(10, -1, 3)
    val nextTurn = turn.nextTurn()

    assertEquals(Turn(3, 0, 1), nextTurn)
  }

  @Test
  fun isWinningMove_true_onWinningResponseValue() {
    assertTrue(Turn(3,  0, 1).isWinningTurn())
    assertTrue(Turn(4, -1, 1).isWinningTurn())
    assertTrue(Turn(2,  1, 1).isWinningTurn())
  }

  @RepeatedTest(10)
  fun isWinningMove_false_onNotWinningResponseValue() {
    val someNotWinningResult = 2 + someInt()
    val turn = Turn(someNotWinningResult * 3, 0, someNotWinningResult)

    assertFalse(turn.isWinningTurn())
  }

  @RepeatedTest(10)
  fun init_cannotCreateTurn_whenTurnIsInvalid() {
    assertThrows(IllegalArgumentException::class.java) {
      val someWrongMove = someInt(from = 2, to = 10)
      val someStartingNumber = 18 - someWrongMove
      Turn(someStartingNumber, someWrongMove, someStartingNumber / 3)
    }
  }

  @RepeatedTest(10)
  fun init_cannotCreateTurn_whenStartingNumberPlugMoveIsNotDivisibleByThree() {
    assertThrows(IllegalArgumentException::class.java) {
      val someResult = someInt(from = 1)
      val someStartingNumber = someResult * 3
      val move = 1

      Turn(someStartingNumber, move, someResult)
    }
  }

  @RepeatedTest(10)
  fun init_canConstructTurn() = assertDoesNotThrow {
    val someResult = someInt(from = 1)
    val someMove = someInt(from = 1, to = 2)
    val expectedInput = (someResult * 3) - someMove

    Turn(expectedInput, someMove, someResult)
  }

  @RepeatedTest(30)
  fun init_justWithStartingNumber_determinesValuesCorrectly_withRandomInput() {
    val someMove = someInt(from = -1, to = 2)
    val someInput = someInt(from = 1) * 3 - someMove

    assertEquals(Turn(someInput, someMove, (someInput + someMove) / 3), Turn(someInput))
  }
}
