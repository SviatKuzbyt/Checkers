package ua.sviatkuzbyt.checkers.ui.game.elements

import android.content.Context
import android.widget.TableLayout
import android.widget.TableRow
import ua.sviatkuzbyt.checkers.data.elements.GameData
import ua.sviatkuzbyt.checkers.ui.game.CellAction


class Checkerboard(
    private val table: TableLayout,
    private val data: GameData,
    private val context: Context
): CellAction {

    private val gameMap = hashMapOf<Int, CellView>()
    private var possibleMovies = mutableListOf<CellView>()
    private var targetPosition = NO_ITEM
    private val takeCandidates = hashMapOf<Int, MutableList<Int>>()
    init {
        createTable()
    }

    private fun createTable() {
        var position = 0
        var isFirstWhite = true

        repeat(8) {
            val tableRow = TableRow(context)

            repeat(4) {
                val cell = CellView(context, data.cells[position], this)
                gameMap[data.cells[position].id] = cell

                if (isFirstWhite){
                    tableRow.addView(WhiteCellView(context))
                    tableRow.addView(cell)
                } else{
                    tableRow.addView(cell)
                    tableRow.addView(WhiteCellView(context))
                }

                position++
            }

            table.addView(tableRow)
            isFirstWhite = !isFirstWhite
        }
    }

    override fun step(id: Int, type: Int, pushSizeOne: Int, pushSizeTwo: Int){
        if (type == data.currentPlayer){
            clearMoves()
            targetPosition = id

            possibleStep(id+pushSizeOne)
            possibleStep(id+pushSizeTwo)
        }
    }

    private fun possibleStep(id: Int){
        gameMap[id]?.let {
            if (it.getType() == CellView.EMPTY){
                it.setType(CellView.MOVE)
                possibleMovies.add(it)
            }
        }
    }

    private fun clearMoves() {
        possibleMovies.forEach {
            it.setType(CellView.EMPTY)
        }
        possibleMovies.clear()
    }

    fun stepQueen(id: Int, type: Int){

    }

    override fun move(id: Int){
        clearMoves()

        // move cell
        gameMap[targetPosition]?.let {
            gameMap[id]?.setType(it.getType())

            it.setType(CellView.EMPTY)
            targetPosition = NO_ITEM
        }

        data.currentPlayer =
            if (data.currentPlayer == CellView.BLACK_PLAYER) CellView.WHITE_PLAYER
            else CellView.BLACK_PLAYER
    }


    companion object{
        const val NO_ITEM = -1
    }
}