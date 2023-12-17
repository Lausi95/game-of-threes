package de.lausi95.gameofthrees.application

import de.lausi95.gameofthrees.domain.model.game.Game
import de.lausi95.gameofthrees.domain.model.player.PlayerRepository
import de.lausi95.gameofthrees.domain.model.turn.MoveResolver
import de.lausi95.gameofthrees.domain.model.turn.TurnPlayedPublisher
import de.lausi95.gameofthrees.domain.model.turn.Turn
import org.springframework.stereotype.Service

@Service
class TurnApplicationService(
  private val playerRepository: PlayerRepository,
  private val turnPlayedPublisher: TurnPlayedPublisher,
  private val moveResolver: MoveResolver,
) {

  /**
   * Plays the first turn of a game.
   *
   * @param game The game to play the turn in.
   */
  fun playFirstTurn(game: Game) = Turn.playFirstTurn(playerRepository.getMe(), game, moveResolver, turnPlayedPublisher)

  /**
   * Plays the next turn in the game.
   *
   * @param turn The current turn.
   */
  fun playNextTurn(turn: Turn) = turn.playNextTurn(playerRepository.getMe(), moveResolver, turnPlayedPublisher)
}
