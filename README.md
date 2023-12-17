# Game of threes

[![tests](https://github.com/Lausi95/game-of-threes/actions/workflows/test.yml/badge.svg?event=push)](https://github.com/Lausi95/game-of-threes/actions/workflows/test.yml)

## Important Notes
- In the introduction text of the assignment, it is stated that the possible
  moves of a player are [1, 0, 1]. This leads to impossible game states, since
  numbers with this input cannot be transformed to a number that is divisible
  by 3.
  I think this is a typo on the assignment and it should state [-1, 0, 1].
  I assume this to be the case for this implementation.

## Build and Run
To build and run the app(s) just go for docker-compose:
```bash
docker-compose up -d
```
This ramps up `kafka` and 2 instances of the `game-of-threes` apps on different ports.

## Launching a game
Depending where you hosted the game and on what port you started it,
fire an `POST` request to the `host` and `port`:

```angular2html
POST http(s)://[host]:[port]/games
```

## TODO
- Add possibility to play the game manually

## Might also be interesting
(☞ﾟヮﾟ)☞ Hire ☞(ﾟヮﾟ)☜ me ☜(ﾟヮﾟ☜)
