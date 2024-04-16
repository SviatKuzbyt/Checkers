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
    private val takeCandidates = hashMapOf<Int, MutableSet<CellView>>()

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

            possibleTakes(id, mutableSetOf())
            possibleStep(id+pushSizeOne)
            possibleStep(id+pushSizeTwo)
        }
    }

    private fun clearMoves() {
        possibleMovies.forEach {
            it.setType(CellView.EMPTY)
        }
        possibleMovies.clear()
        takeCandidates.clear()
    }

    private fun possibleTakes(id: Int, candidates: MutableSet<CellView>){
        checkTake(id, 9, candidates.toMutableSet())
        checkTake(id, 11, candidates.toMutableSet())
        checkTake(id, -9, candidates.toMutableSet())
        checkTake(id, -11, candidates.toMutableSet())
    }

    private fun checkTake(id: Int, pushSize: Int, candidates: MutableSet<CellView>){
        gameMap[id + pushSize]?.let { enemyCell ->
            if (enemyCell.getType() != CellView.EMPTY && enemyCell.getPlayer() != data.currentPlayer){
                val emptyId = id + pushSize + pushSize
                gameMap[emptyId]?.let { emptyCell ->
                    if (emptyCell.getType() == CellView.EMPTY){

                        emptyCell.setType(CellView.MOVE)
                        possibleMovies.add(emptyCell)
                        candidates.add(enemyCell)
                        takeCandidates[emptyId] = candidates

                        possibleTakes(emptyId, candidates)
                    }
                }
            }
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

    override fun stepQueen(id: Int, type: Int){
        if (type == data.currentPlayer){
            clearMoves()
            targetPosition = id

            possibleQueenSteps(id, 9, mutableSetOf())
            possibleQueenSteps(id, 11, mutableSetOf())
            possibleQueenSteps(id, -9, mutableSetOf())
            possibleQueenSteps(id, -11, mutableSetOf())
        }
    }

    private fun possibleQueenSteps(id: Int, pushSize: Int, candidates: MutableSet<CellView>){
        val nextId = id+pushSize
        gameMap[nextId]?.let { nextCell ->
            if (nextCell.getType() == CellView.EMPTY){
                nextCell.setType(CellView.MOVE)
                possibleMovies.add(nextCell)
                takeCandidates[nextId] = candidates
                possibleQueenSteps(nextId, pushSize, candidates.toMutableSet())
            }
            else if(nextCell.getPlayer() != data.currentPlayer){
                val nextEnemyId = nextId + pushSize
                gameMap[nextEnemyId]?.let { emptyCell ->
                    if (emptyCell.getType() == CellView.EMPTY){
                        emptyCell.setType(CellView.MOVE)
                        possibleMovies.add(emptyCell)
                        candidates.add(nextCell)
                        takeCandidates[nextEnemyId] = candidates
                        possibleTakes(nextEnemyId, candidates.toMutableSet())
                        possibleQueenSteps(nextEnemyId, pushSize, candidates)
                    }
                }

            }
        }
    }

    override fun move(id: Int){
        //remove enemies
        takeCandidates[id]?.let {
            it.forEach { cell ->
                cell.setType(CellView.EMPTY)

                if (data.currentPlayer == CellView.BLACK_PLAYER)
                    data.whiteCount --
                else data.blackCount --
            }
        }


        clearMoves()

        // move cell
        gameMap[targetPosition]?.let { oldCell ->
            gameMap[id]?.let{ newCell ->
                if (id-8 < 11 && data.currentPlayer == CellView.WHITE_PLAYER)
                    newCell.setType(CellView.WHITE_QUEEN)
                else if(id+8 > 88 && data.currentPlayer == CellView.BLACK_PLAYER)
                    newCell.setType(CellView.BLACK_QUEEN)
                else
                    newCell.setType(oldCell.getType())
            }

            oldCell.setType(CellView.EMPTY)
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