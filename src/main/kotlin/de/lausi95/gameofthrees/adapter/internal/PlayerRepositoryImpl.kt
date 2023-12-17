package de.lausi95.gameofthrees.adapter.internal

import de.lausi95.gameofthrees.domain.player.Player
import de.lausi95.gameofthrees.domain.player.PlayerRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
private class PlayerRepositoryImpl(@Value("\${game-of-three.player-id}") private val playerId: String) : PlayerRepository {

  private val me = Player(playerId)

  override fun getMe(): Player {
    return me
  }
}
