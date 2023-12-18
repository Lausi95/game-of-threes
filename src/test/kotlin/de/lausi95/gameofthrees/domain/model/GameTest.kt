package de.lausi95.gameofthrees.domain.model

import de.lausi95.gameofthrees.domain.model.game.Game
import de.lausi95.gameofthrees.domain.model.game.GameStartedPublisher
import de.lausi95.gameofthrees.domain.model.game.FirstNumberGenerator
import de.lausi95.gameofthrees.someInt
import de.lausi95.gameofthrees.somePlayer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest

class GameTest {

  @RepeatedTest(10)
  fun start_shouldTriggerOnGameStart_whenGameIsValid() {
    val somePlayer = somePlayer()
    val someStartNumber = someInt(from = 2)

    val firstNumberGenerator = object : FirstNumberGenerator {
      override fun generateFirstNumber(): Int {
        return someStartNumber
      }
    }

    var publishGameFunctionTriggered = false

    val gameStartedPublisher = object : GameStartedPublisher {
      override fun publishGameStarted(game: Game) {
        publishGameFunctionTriggered = true
        assertEquals(someStartNumber, game.firstNumber)
        assertEquals(somePlayer.playerId, game.initiatorPlayerId)
      }
    }

    Game.start(somePlayer, firstNumberGenerator, gameStartedPublisher)
    assertTrue(publishGameFunctionTriggered)
  }

  @RepeatedTest(10)
  fun start_shouldNotTriggerOnGameStart_whenStartingNumberIsInvalid() {
    val somePlayer = somePlayer()
    val someInvalidStartNumber = someInt(from = -1000, to = 2)

    val firstNumberGenerator = object : FirstNumberGenerator {
      override fun generateFirstNumber(): Int {
        return someInvalidStartNumber
      }
    }

    var publishGameFunctionTriggered = false
    val gameStartedPublisher = object : GameStartedPublisher {
      override fun publishGameStarted(game: Game) {
        publishGameFunctionTriggered = true
      }
    }
    Game.start(somePlayer, firstNumberGenerator, gameStartedPublisher)
    assertFalse(publishGameFunctionTriggered)
  }
}
