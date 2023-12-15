package de.lausi95.gameofthrees

import java.time.Instant
import kotlin.random.Random

class PbtRandom(seed: Int, private val random: Random) {
  init {
    println("SEED: $seed")
  }

  constructor(seed: Int) : this(seed, Random(seed))

  fun nextInt(from: Int = 0, to: Int = 100) = random.nextInt(from, to)
}

val random = PbtRandom(System.getenv("PBT_SEED")?.toInt() ?: Instant.now().nano)

fun someInt(from: Int = 0, to: Int = 100) = random.nextInt(from, to)
