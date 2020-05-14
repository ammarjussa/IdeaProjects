package board

import board.Direction.*
import java.lang.IllegalArgumentException

fun createSquareBoard(width: Int): SquareBoard = MySquareBoard(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = MyGameBoard(width)

open class MySquareBoard(override val width: Int): SquareBoard {
    private var cellBoard = mutableListOf<List<Cell>>()
    init {
        for(i in 1 .. width) {
            var cells = mutableListOf<Cell>()
            for(j in 1 .. width) {
                cells.add(Cell(i,j))
            }
            cellBoard.add(cells)
        }
    }

    override fun getCellOrNull(i: Int, j:Int ): Cell? {
        if(i in 1..width && j in 1..width) {
            return cellBoard[i-1][j-1]
        }
        return null
    }

    override fun getCell(i: Int, j: Int): Cell {
        if(i in 1..width && j in 1..width) {
            return cellBoard[i-1][j-1]
        }

        throw IllegalArgumentException("Not Such cell")
    }

    override fun getAllCells(): Collection<Cell> {
        var ans = mutableListOf<Cell>()
        for(cells in cellBoard) {
            for(cell in cells) {
                ans.add(cell)
            }
        }

        return ans
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        var ans = mutableListOf<Cell>()
        var start = jRange.first
        var end = 0
        end = if(jRange.last > width) {
            width
        }

        else {
            jRange.last
        }


        var reversed = false
        if(start > end) {
            reversed = true
            var temp = start
            start=end
            end=temp
        }

        for(k in start..end) {
            ans.add(cellBoard[i-1][k-1])
        }

        return if (reversed) ans.reversed() else ans
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        var ans = mutableListOf<Cell>()
        var start = iRange.first
        var end = 0
        end = if(iRange.last > width) {
            width
        }

        else {
            iRange.last
        }

        var reversed = false
        if(start > end) {
            reversed = true
            var temp = start
            start=end
            end=temp
        }

        for(k in start..end) {
            ans.add(cellBoard[k-1][j-1])
        }

        return if (reversed) ans.reversed() else ans
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        var ith = this.i
        var jth = this.j

        when(direction) {
            UP -> ith -= 1
            DOWN -> ith+=1
            LEFT -> jth-=1
            RIGHT -> jth+=1
        }

        if(ith in 1..width && jth in 1..width) return cellBoard[ith-1][jth-1]
        return null
    }
}

open class MyGameBoard<T>(width: Int) : MySquareBoard(width), GameBoard<T> {
    private val board = getAllCells()
            .map { it to null }
            .toMap<Cell, T?>()
            .toMutableMap()


    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return board.entries.filter { predicate(it.value) }.map{it.key}
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return board.entries.find { predicate(it.value) }?.key
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return board.entries.any {predicate(it.value)}
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return board.entries.all {predicate(it.value)}
    }

    override operator fun get(cell: Cell): T? = board[cell]

    override operator fun set(cell: Cell, value: T?) {
        board[cell]=value
    }

}
