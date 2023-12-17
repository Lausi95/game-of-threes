package de.lausi95.gameofthrees.domain.game

import de.lausi95.gameofthrees.someInt
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.RepeatedTest

class RandomStartNumberGeneratorTest {

  @RepeatedTest(100)
  fun testGenerateStartNumber_generatedNumberIsWithinGivenBounds() {
    val someMaxValue = someInt(from = 3, to = 10)
    val randomStartNumberGenerator = RandomStartNumberGenerator(someMaxValue)

    val randomStartNumber = randomStartNumberGenerator.generateStartValue()

    Assertions.assertThat(randomStartNumber).isGreaterThan(1)
    Assertions.assertThat(randomStartNumber).isLessThan(someMaxValue)
  }
}
