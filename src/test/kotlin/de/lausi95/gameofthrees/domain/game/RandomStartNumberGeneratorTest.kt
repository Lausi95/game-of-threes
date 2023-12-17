package de.lausi95.gameofthrees.domain.game

import de.lausi95.gameofthrees.someInt
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest

class RandomStartNumberGeneratorTest {

  @RepeatedTest(100)
  fun testGenerateStartNumber_generatedNumberIsWithinGivenBounds() {
    val someMaxValue = someInt(from = 1, to = 1000)
    val randomStartNumberGenerator = RandomStartNumberGenerator(someMaxValue)

    val randomStartNumber = randomStartNumberGenerator.generateStartValue()

    assertTrue(randomStartNumber > 1)
    assertTrue(randomStartNumber <= someMaxValue)
  }
}
