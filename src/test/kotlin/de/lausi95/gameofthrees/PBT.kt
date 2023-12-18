package de.lausi95.gameofthrees

import de.lausi95.gameofthrees.domain.model.game.Game
import de.lausi95.gameofthrees.domain.model.player.Player
import java.time.Instant
import kotlin.random.Random

/**
 * Generator functions for Property-Based testing
 */

private class PbtRandom(seed: Int, private val random: Random) {

  private val charPool =  ('a'..'z') + ('A'..'Z') + ('0'..'9')

  init {
    println("PBT_SEED: $seed")
  }

  constructor(seed: Int) : this(seed, Random(seed))

  fun nextInt(from: Int = 0, to: Int = 100) = random.nextInt(from, to)

  fun nextString(length: Int) = (1..length).map { nextInt(0, charPool.size).let { charPool[it] }}.joinToString("")
}

private val random = PbtRandom(System.getenv("PBT_SEED")?.toInt() ?: Instant.now().nano)

fun someInt(from: Int = 0, to: Int = 100) = random.nextInt(from, to)

fun someString(length: Int = 10): String = random.nextString(length)

fun somePlayer(playerId: String = someString()) = Player(playerId)

fun someGame(initiationPlayerId: String = someString(), startNumber: Int = someInt(3, 1000)) = Game(startNumber, initiationPlayerId)
