package de.lausi95.gameofthrees.adapter.http

import de.lausi95.gameofthrees.domain.model.turn.MoveStrategy
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

// TODO Need decide on how to recover moves, if the service is shut down, but
//  the moves are not played yet.
@RestController
@RequestMapping("/moves")
private class HttpMoveStrategy : MoveStrategy {

  val moves: MutableMap<UUID, Pair<Int, (Int) -> Unit>> = mutableMapOf()

  override fun name(): String = "MANUAL"

  override fun resolveMove(number: Int, moveCallback: (Int) -> Unit) {
    moves[UUID.randomUUID()] = Pair(number, moveCallback)
  }

  @GetMapping
  fun getOpenMoves(): Map<UUID, Int> = moves.mapValues { e -> e.value.first }

  @PostMapping("/{moveId}")
  fun playMove(@PathVariable moveId: UUID, @RequestParam move: Int): ResponseEntity<String> {
    try {
      moves[moveId]?.second?.invoke(move)
      moves.remove(moveId)
      return ResponseEntity.ok("You played move $move")
    } catch (ex: Exception) {
      return ResponseEntity.badRequest().body("Your move ($move) cannot be played. Reason: ${ex.message}")
    }
  }
}
