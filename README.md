# Game of threes

[![tests](https://github.com/Lausi95/game-of-threes/actions/workflows/test.yml/badge.svg?event=push)](https://github.com/Lausi95/game-of-threes/actions/workflows/test.yml)

## Important annotation
In the task definition, it is stated that the possible
moves of a player are [1, 0, 1]. This leads to impossible game states, since
some numbers (like 10) cannot be transformed to a number which is divisible
by 3, with those moves.

I think there is a typo on the definition and it should state [-1, 0, 1].
I assume this to be the case for this implementation.

## Technologies & Paradigms
- Kotlin
- Spring-Boot
- Kafka
- Domain-Driven Design
- Onion Architecture
- Property-Based Testing

## Build and Run
To build and run the app(s) just go for docker-compose:
```bash
docker-compose up -d
```
This ramps up `kafka` and 2 instances of the `game-of-threes` apps on different ports.

## How to play

### Launching a game
Depending where you hosted the players and on what port you started then,
fire an `POST` request to the `host` and `port`:

```
POST http(s)://[host]:[port]/games
```

### Playing the game

Now the player you posted the request on requests a new game.
Other players listening on the game queue, respond to it and play their turns.

The initiator will receive the played turn and can now respond to it again as well.
Depending on the configuration the turns are resolved automatically, or you can
play for yourself.

The game will go on until on player can reduce the number to 1.

## Configuration

The configuration of a player is done by environment variables.

`KAFKA_URL`: **mandatory** Url to a kafka node.

`PLAYER_ID`: **mandatory** ID of the player, playing the game. If multiple players should
play the game, the `PLAYER_ID` should be different for each instance.

`MOVE_RESOLVER`: **optional** Decides if the player plays manually or with user input.
To play manual, set the variable to `MANUAL`. Default: `AUTOMATIC`

## Might also be interesting
(☞ﾟヮﾟ)☞ Hire ☞(ﾟヮﾟ)☜ me ☜(ﾟヮﾟ☜)
