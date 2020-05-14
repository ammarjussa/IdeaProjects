package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer): Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        var index = 0
        for(i in 1..4) {
            for(j in 1..4) {
                board.getCellOrNull(i,j)?.let { board[it] = initializer.initialPermutation[index]}
                index+=1
                if(index == 15) break
            }
            if(index == 15) break
        }
        
    }

    override fun canMove(): Boolean = board.any { it == null }

    override fun hasWon(): Boolean {
        var valList = board.getAllCells().map { board[it] }
        valList = valList.filter { it!=null }
        println(valList)
        for(i in valList.indices) {
            if(valList[i]!=i+1) {
                return false
            }
        }

        return true

    }

    override fun processMove(direction: Direction) {
        var nullC = board.filter { it == null }
        var nullCell = nullC.first()
        when(direction) {
            Direction.UP -> {
                var valCell = board.getCell(nullCell.i+1,nullCell.j)
                board[nullCell]=board[valCell]
                board[valCell]=null

            }

            Direction.DOWN ->  {
                var valCell = board.getCell(nullCell.i-1,nullCell.j)
                board[nullCell]=board[valCell]
                board[valCell]=null
            }

            Direction.LEFT ->  {
                var valCell = board.getCell(nullCell.i,nullCell.j+1)
                board[nullCell]=board[valCell]
                board[valCell]=null
            }

            Direction.RIGHT ->  {
                var valCell = board.getCell(nullCell.i,nullCell.j-1)
                board[nullCell]=board[valCell]
                board[valCell]=null
            }


        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}