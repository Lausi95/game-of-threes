package de.lausi95.gameofthrees.domain.model.turn

val AUTOMATIC_MOVE_RESOLVER = object : MoveResolver {
  override fun resolveMove(number: Int): Int = number.resolveNextMove()
}

val MANUAL_MOVE_RESOLVER = object : MoveResolver {
  override fun resolveMove(number: Int): Int {
    while (true) {
      try {
        print("Your move for $number >> ")
        val input = readln()
        val move = input.toInt()
        if (move.isValid() && ((number + move) % 3) == 0) {
          return move
        }
        println("Move $move does not work for number $number! Try again.")
      } catch (ex: Exception) {
        println("Invalid Input!")
      }
    }
  }
}

interface MoveResolver {

  fun resolveMove(number: Int): Int
}
