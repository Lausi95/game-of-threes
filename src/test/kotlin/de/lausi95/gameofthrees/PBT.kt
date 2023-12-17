package de.lausi95.gameofthrees

import de.lausi95.gameofthrees.domain.player.Player
import java.time.Instant
import kotlin.random.Random

class PbtRandom(seed: Int, private val random: Random) {
  init {
    println("PBT_SEED: $seed")
  }

  constructor(seed: Int) : this(seed, Random(seed))

  fun nextInt(from: Int = 0, to: Int = 100) = random.nextInt(from, to)

  fun nextString(length: Int) = String(random.nextBytes("abcdefghijklmnopqrstuvwxyz".encodeToByteArray(), length))
}

val random = PbtRandom(System.getenv("PBT_SEED")?.toInt() ?: Instant.now().nano)

fun someInt(from: Int = 0, to: Int = 100) = random.nextInt(from, to)

fun someString(length: Int = 10): String = random.nextString(length)

fun somePlayer(playerId: String? = null) = Player(playerId ?: someString())
