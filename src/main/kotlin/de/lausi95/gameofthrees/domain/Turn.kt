package de.lausi95.gameofthrees.domain

import java.util.*
import java.util.stream.IntStream
import kotlin.streams.toList

fun Int.resolveValidMoves(): List<Int> = IntStream.range(0, this).map { it - this / 2 }.toList()

fun Int.isValid(): Boolean = VALID_MOVES.contains(this)

fun Int.distanceToPrevNumberDivisibleBy(divisor: Int): Int = (this / divisor) * divisor - this
fun Int.distanceToNextNumberDivisibleBy(divisor: Int): Int = (this / divisor + 1) * divisor - this

fun Int.resolveResponseNumber(divisor: Int, move: Int): Int = (this + move) / divisor
fun Int.resolveResponseNumber(divisor: Int): Int = this.resolveResponseNumber(divisor, this.resolveNextMove(divisor))

fun Int.resolveStartingNumber(divisor: Int, move: Int): Int = this * divisor - move

private fun Int.resolveNextMove(divisor: Int): Int {
  return listOf(
    this.distanceToNextNumberDivisibleBy(divisor),
    this.distanceToPrevNumberDivisibleBy(divisor)
  ).filter { VALID_MOVES.contains(it) }.min()
}

const val DIVISOR = 3

val VALID_MOVES = DIVISOR.resolveValidMoves()

data class Turn(val startingNumber: Int, val move: Int, val responseNumber: Int) : Formattable {
  init {
    if (!move.isValid())
      throw IllegalArgumentException("Invalid move. Valid Moves: $VALID_MOVES")
    if (responseNumber.resolveStartingNumber(DIVISOR, move) != startingNumber)
      throw IllegalArgumentException("Impossible move")
  }

  constructor(startingNumber: Int): this(
    startingNumber,
    startingNumber.resolveNextMove(DIVISOR),
    startingNumber.resolveResponseNumber(DIVISOR))

  fun nextTurn(): Turn = Turn(
    responseNumber,
    responseNumber.resolveNextMove(DIVISOR),
    responseNumber.resolveResponseNumber(DIVISOR))

  fun isWinningTurn(): Boolean = responseNumber == 1

  override fun formatTo(formatter: Formatter?, flags: Int, width: Int, precision: Int) {
    formatter?.format("Given Number: %d, Move: %d, Result: %d", startingNumber, move, responseNumber)
  }
}
