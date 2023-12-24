package de.lausi95.gameofthrees.domain.model

import de.lausi95.gameofthrees.domain.model.game.Game
import de.lausi95.gameofthrees.domain.model.game.GameStartedPublisher
import de.lausi95.gameofthrees.domain.model.game.FirstNumberGenerator
import de.lausi95.gameofthrees.someInt
import de.lausi95.gameofthrees.somePlayer
import org.junit.jupiter.api.RepeatedTest
import org.mockito.kotlin.*

class GameTest {

  @RepeatedTest(10)
  fun start_shouldTriggerOnGameStart_whenGameIsValid() {
    val somePlayer = somePlayer()
    val someStartNumber = someInt(from = 2)
    val gameStartedPublisher = mock<GameStartedPublisher> {}
    val firstNumberGenerator = mock<FirstNumberGenerator> {
      on { generateFirstNumber() } doReturn someStartNumber
    }

    Game.start(somePlayer, firstNumberGenerator, gameStartedPublisher)

    verify(gameStartedPublisher).publishGameStarted(any())
  }

  @RepeatedTest(10)
  fun start_shouldNotTriggerOnGameStart_whenStartingNumberIsInvalid() {
    val somePlayer = somePlayer()
    val someInvalidStartNumber = someInt(from = -1000, to = 2)
    val firstNumberGenerator = mock<FirstNumberGenerator> {
      on { generateFirstNumber() } doReturn someInvalidStartNumber
    }
    val gameStartedPublisher = mock<GameStartedPublisher> {}
    doReturn(someInvalidStartNumber).`when`(firstNumberGenerator).generateFirstNumber()

    Game.start(somePlayer, firstNumberGenerator, gameStartedPublisher)

    verify(gameStartedPublisher, times(0)).publishGameStarted(any())
  }
}
