package de.lausi95.gameofthrees.adapter.kafka.dto

data class TurnDto(
  val playerId: String,
  val opponentPlayerId: String,
  val startingNumber: Int,
  val move: Int,
  val responseNumber: Int,
)
