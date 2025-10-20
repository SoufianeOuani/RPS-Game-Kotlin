package com.example.problemsolvinglevel01_kotlin

import kotlin.random.Random

//__________enums__________
enum class EnGameChoice(val value: Int)
{
    Stone(1), Paper(2), Scissors(3)
}

enum class EnWinner(val value: Int)
{
    Player(1), Computer(2), Draw(3)
}

//__________Structs__________
data class StRoundInfo(
    var roundNumber: Int = 0,
    var playerChoice: EnGameChoice = EnGameChoice.Stone,
    var computerChoice: EnGameChoice = EnGameChoice.Stone,
    var roundWinner: EnWinner = EnWinner.Draw,
    var roundWinnerName: String = ""
)

data class StGameResults(
    var gameRounds:Int = 0,
    var playerWinTimes: Int = 0,
    var computerWinTimes: Int = 0,
    var drawTimes: Int = 0,
    var finalGameWinner: EnWinner = EnWinner.Draw,
    var finalGameWinnerName: String = ""
)

//__________Functions__________
fun randomNumbersGenerator(from: Int, to: Int):Int =
    Random.nextInt(from, to+1)

fun getComputerChoice(): EnGameChoice
{
    return when(randomNumbersGenerator(1, 3))
    {
        1 -> EnGameChoice.Stone
        2 -> EnGameChoice.Paper
        else -> EnGameChoice.Scissors
    }
}

fun readPlayerChoice(): EnGameChoice
{
    var playerChoice = 0

    do
    {
        print("Your choice: [1]:Stone, [2]:Paper, [3]:Scissors? ")
        playerChoice = readLine()?.toIntOrNull() ?: 0
    }while (playerChoice !in 1..3)

    return when(playerChoice)
    {
        1 -> EnGameChoice.Stone
        2 -> EnGameChoice.Paper
        else -> EnGameChoice.Scissors
    }
}

fun whoWonTheRound(roundInfo: StRoundInfo): EnWinner
{
    if (roundInfo.playerChoice == roundInfo.computerChoice)
        return EnWinner.Draw

    return when(roundInfo.playerChoice)
    {
        EnGameChoice.Stone -> if (roundInfo.computerChoice == EnGameChoice.Paper) EnWinner.Computer else EnWinner.Player
        EnGameChoice.Paper -> if(roundInfo.computerChoice == EnGameChoice.Scissors) EnWinner.Computer else EnWinner.Player
        EnGameChoice.Scissors -> if (roundInfo.computerChoice == EnGameChoice.Stone) EnWinner.Computer else EnWinner.Player
    }
}

fun whoWonTheGame(playerWinTimes: Int, computerWinTimes: Int): EnWinner
{
    return when
    {
        playerWinTimes > computerWinTimes -> EnWinner.Player
        computerWinTimes > playerWinTimes -> EnWinner.Computer
        else -> EnWinner.Draw
    }
}

fun choiceName(choice: EnGameChoice):String
{
    return when(choice)
    {
        EnGameChoice.Stone -> "Stone"
        EnGameChoice.Paper -> "Paper"
        EnGameChoice.Scissors -> "Scissors"
    }
}

fun winnerName(winner: EnWinner):String
{
    return when(winner)
    {
        EnWinner.Player -> "Player"
        EnWinner.Computer -> "Computer"
        EnWinner.Draw -> "Draw"
    }
}

fun printRoundResults(roundInfo: StRoundInfo) {
    println("\n____________ Round [${roundInfo.roundNumber}] ____________\n")
    println("Player1 Choice : ${choiceName(roundInfo.playerChoice)}")
    println("Computer Choice: ${choiceName(roundInfo.computerChoice)}")
    println("Round Winner   : [${roundInfo.roundWinnerName}]")
    println("_________________________________________\n")
}

fun playGame(howManyRounds:Int): StGameResults
{
    var playerWins = 0
    var computerWins = 0
    var drawTimes = 0
    val roundInfo = StRoundInfo()

    for (gameRound in 1..howManyRounds)
    {
        println("\nRound [$gameRound] begins:")
        roundInfo.roundNumber = gameRound
        roundInfo.playerChoice = readPlayerChoice()
        roundInfo.computerChoice = getComputerChoice()
        roundInfo.roundWinner = whoWonTheRound(roundInfo)
        roundInfo.roundWinnerName = winnerName(roundInfo.roundWinner)

        when (roundInfo.roundWinner)
        {
            EnWinner.Player -> playerWins++
            EnWinner.Computer -> computerWins++
            EnWinner.Draw -> drawTimes++
        }

        printRoundResults(roundInfo)
    }

    val gameWinner = whoWonTheGame(playerWins, computerWins)

    return StGameResults(
        gameRounds = howManyRounds,
        playerWinTimes = playerWins,
        computerWinTimes = computerWins,
        drawTimes = drawTimes,
        finalGameWinner = gameWinner,
        finalGameWinnerName = winnerName(gameWinner)
    )
}

fun gameHeader()
{
    println("\n===============================")
    println("🎮 Rock Paper Scissors - Kotlin")
    println("===============================\n")
}

fun gameOverScreen(gameResults: StGameResults)
{
    println("\nGame Over! 🏁 Winner: ${gameResults.finalGameWinnerName}")
    println("\nResults Summary:")
    println("Player Wins  : ${gameResults.playerWinTimes}")
    println("Computer Wins: ${gameResults.computerWinTimes}")
    println("Draws        : ${gameResults.drawTimes}")
}

fun startGame()
{
    var playAgain = 'Y'

    do
    {
        gameHeader()

        print("How many rounds to play? ")
        val howManyRounds = readLine()?.toIntOrNull() ?: 3

        val gameResults = playGame(howManyRounds)

        gameOverScreen(gameResults)

        print("Do you want to play again? (Y/N): ")
        playAgain = readln()?.uppercase()?.firstOrNull() ?: 'N' 

    }while (playAgain == 'Y')
}

fun main()
{
    startGame()
}
