# Game of threes

[![tests](https://github.com/Lausi95/game-of-threes/actions/workflows/test.yml/badge.svg?event=push)](https://github.com/Lausi95/game-of-threes/actions/workflows/test.yml)

## Important Notes
- In the introduction text of the assignment, it is stated that the possible
  moves of a player are [1, 0, 1]. This leads to impossible game states, since
  numbers with this input cannot be transformed to a number that is divisible
  by 3.
  I suggest this is a typo on the assignment and should state [-1, 0, 1].
  I assume this to be the case for this implementation.
