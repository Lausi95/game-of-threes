package de.lausi95.gameofthrees.adapter.rest

import de.lausi95.gameofthrees.domain.Turn
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/turns")
class TurnController {

  @PostMapping
  fun playTurn(@RequestBody turn: Turn): Turn {
    return turn.nextTurn()
  }
}
