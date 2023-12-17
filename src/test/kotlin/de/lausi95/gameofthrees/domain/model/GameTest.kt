package de.lausi95.gameofthrees.domain.model

import de.lausi95.gameofthrees.domain.model.game.Game
import de.lausi95.gameofthrees.domain.model.game.GameStartedPublisher
import de.lausi95.gameofthrees.domain.model.game.StartNumberGenerator
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

    val gameStartedPublisher = object : GameStartedPublisher {
      override fun publishGameStarted(game: Game) {
        publishGameFunctionTriggered = true
        assertEquals(someStartNumber, game.startNumber)
        assertEquals(somePlayer.playerId, game.initiatorPlayerId)
      }
    }

    Game.start(somePlayer, startNumberGenerator, gameStartedPublisher)
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
    val gameStartedPublisher = object : GameStartedPublisher {
      override fun publishGameStarted(game: Game) {
        publishGameFunctionTriggered = true
      }
    }
    Game.start(somePlayer, startNumberGenerator, gameStartedPublisher)
    assertFalse(publishGameFunctionTriggered)
  }
}
