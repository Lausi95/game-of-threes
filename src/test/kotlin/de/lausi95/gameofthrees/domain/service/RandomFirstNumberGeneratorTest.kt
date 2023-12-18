package de.lausi95.gameofthrees.domain.service

import de.lausi95.gameofthrees.domain.model.game.RandomFirstNumberGenerator
import de.lausi95.gameofthrees.someInt
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.RepeatedTest

class RandomFirstNumberGeneratorTest {

  @RepeatedTest(100)
  fun testGenerateStartNumber_generatedNumberIsWithinGivenBounds() {
    val someMaxValue = someInt(from = 3, to = 10)
    val randomStartNumberGenerator = RandomFirstNumberGenerator(someMaxValue)

    val randomStartNumber = randomStartNumberGenerator.generateFirstNumber()

    Assertions.assertThat(randomStartNumber).isGreaterThan(1)
    Assertions.assertThat(randomStartNumber).isLessThan(someMaxValue)
  }
}
