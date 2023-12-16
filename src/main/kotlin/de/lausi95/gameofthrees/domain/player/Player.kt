package de.lausi95.gameofthrees.domain.player

data class Player(val playerId: String)

interface PlayerRepository {

  fun getMe(): Player
}
