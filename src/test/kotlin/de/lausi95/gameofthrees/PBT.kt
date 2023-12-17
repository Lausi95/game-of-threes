package de.lausi95.gameofthrees

import de.lausi95.gameofthrees.domain.model.player.Player
import java.time.Instant
import kotlin.random.Random

private val CHAR_POOL =  ('a'..'z') + ('A'..'Z') + ('0'..'9')

class PbtRandom(seed: Int, private val random: Random) {
  init {
    println("PBT_SEED: $seed")
  }

  constructor(seed: Int) : this(seed, Random(seed))

  fun nextInt(from: Int = 0, to: Int = 100) = random.nextInt(from, to)

  fun nextString(length: Int) = (1..length).map { nextInt(0, CHAR_POOL.size).let { CHAR_POOL[it] }}.joinToString("")
}

val random = PbtRandom(System.getenv("PBT_SEED")?.toInt() ?: Instant.now().nano)

fun someInt(from: Int = 0, to: Int = 100) = random.nextInt(from, to)

fun someString(length: Int = 10): String = random.nextString(length)

fun somePlayer(playerId: String? = null) = Player(playerId ?: someString())
