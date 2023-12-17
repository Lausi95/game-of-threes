package de.lausi95.gameofthrees.domain.game

import de.lausi95.gameofthrees.someInt
import de.lausi95.gameofthrees.somePlayer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest

class GameTest {

  @RepeatedTest(10)
  fun start_shouldTriggerOnGameStart_whenGameIsValid() {
    val somePlayer = somePlayer()
    val someStartNumber = someInt()

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
}
