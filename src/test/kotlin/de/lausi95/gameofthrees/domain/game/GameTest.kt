package de.lausi95.gameofthrees.domain.game

import de.lausi95.gameofthrees.someInt
import de.lausi95.gameofthrees.somePlayer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest

class GameTest {

  @RepeatedTest(10)
  fun start_shouldTriggerOnGameStart_whenGameIsValid() {
    val somePlayer = somePlayer()
    val someStartNumber = someInt(from = 2)

    val startNumberGenerator = object : StartNumberGenerator {
      override fun generateStartValue(): Int {
        return someStartNumber
      }
    }

    var publishGameFunctionTriggered = false
    Game.start(somePlayer, startNumberGenerator) {
      publishGameFunctionTriggered = true
      assertEquals(someStartNumber, it.startNumber)
      assertEquals(somePlayer.playerId, it.initiatorPlayerId)
    }
    assertTrue(publishGameFunctionTriggered)
  }

  @RepeatedTest(10)
  fun start_shouldNotTriggerOnGameStart_whenStartingNumberIsInvalid() {
    val somePlayer = somePlayer()
    val someInvalidStartNumber = someInt(from = -1000, to = 2)

    val startNumberGenerator = object : StartNumberGenerator {
      override fun generateStartValue(): Int {
        return someInvalidStartNumber
      }
    }

    var publishGameFunctionTriggered = false
    Game.start(somePlayer, startNumberGenerator) {
      publishGameFunctionTriggered = true
    }
    assertFalse(publishGameFunctionTriggered)
  }
}
