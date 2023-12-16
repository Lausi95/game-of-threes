package de.lausi95.gameofthrees.domain.game

interface StartValueStrategy {

  fun generateStartValue(): Int
}
