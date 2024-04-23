package ua.sviatkuzbyt.checkers.game.checkerboard

import android.content.Context
import android.widget.TableLayout
import android.widget.TableRow
import ua.sviatkuzbyt.checkers.game.elements.CellAction
import ua.sviatkuzbyt.checkers.game.elements.GameAction
import ua.sviatkuzbyt.checkers.game.elements.GameData

class Checkerboard(
    private val table: TableLayout,
    private val data: GameData,
    private val context: Context,
    private val action: GameAction
): CellAction {

    private val gameMap = hashMapOf<Int, CellView>()
    private var possibleMovies = mutableSetOf<CellView>()
    private var targetPosition = -1
    private val takeCandidates = hashMapOf<Int, MutableSet<CellView>>()

    init { createTable() }

    private fun createTable() {
        var position = 0
        var isFirstWhite = true

        repeat(8) {
            val tableRow = TableRow(context)

            repeat(4) {
                if (data.cells[position].type == CellView.MOVE)
                    data.cells[position].type = CellView.EMPTY

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
    //перевірка можливих ходів на сусідні клітинки так і на побиття шашок
    override fun step(id: Int, type: Int, pushSizeOne: Int, pushSizeTwo: Int){
        if (type == data.currentPlayer){
            clearMoves()
            targetPosition = id

            possibleTakes(id, mutableSetOf())
            possibleStep(id+pushSizeOne)
            possibleStep(id+pushSizeTwo)
        }
    }

    //перед кожною дією очищаємо всі дані та перегляди
    private fun clearMoves() {
        possibleMovies.forEach {
            it.setType(CellView.EMPTY)
        }
        possibleMovies.clear()
        takeCandidates.clear()
    }

    //перевірка чи можна побити шашку
    private fun possibleTakes(id: Int, candidates: MutableSet<CellView>){
        checkTake(id, 9, candidates.toMutableSet())
        checkTake(id, 11, candidates.toMutableSet())
        checkTake(id, -9, candidates.toMutableSet())
        checkTake(id, -11, candidates.toMutableSet())
    }

    private fun checkTake(id: Int, pushSize: Int, candidates: MutableSet<CellView>){
        //якщо наступна клітинка - ворог
        gameMap[id + pushSize]?.let { enemyCell ->
            if (enemyCell.getType() != CellView.EMPTY && enemyCell.getPlayer() != data.currentPlayer){
                val emptyId = id + pushSize + pushSize
                // і наступна, наступна - пуста
                gameMap[emptyId]?.let { emptyCell ->
                    if (emptyCell.getType() == CellView.EMPTY){

                        //позначаємо можливй хід, зберігаєм його
                        emptyCell.setType(CellView.MOVE)
                        possibleMovies.add(emptyCell)
                        candidates.add(enemyCell)
                        takeCandidates[emptyId] = candidates

                        //перевіряємо чи можна побити ще шашки
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
            //якщо клітинка пуста, додаєм можливий хід і перевіряємо наступну
            if (nextCell.getType() == CellView.EMPTY){
                nextCell.setType(CellView.MOVE)
                possibleMovies.add(nextCell)
                takeCandidates[nextId] = candidates
                possibleQueenSteps(nextId, pushSize, candidates.toMutableSet())
            }
            //якщо на клітинці - ворог, то перевіряємо чи наступна пуста і наступні клітинки та ворогів
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
        //прибираємо ворогів. які ми побили
        takeCandidates[id]?.let {
            it.forEach { cell ->
                cell.setType(CellView.EMPTY)

                if (data.currentPlayer == CellView.BLACK_PLAYER)
                    data.whiteCount --
                else data.blackCount --
            }
        }

        clearMoves()

        //Переміщщаєм шашку на нову клітинку (і робимо її королевою за потреби)
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
            targetPosition = -1
        }

        //повідомляємо діяльності, що ми зробили хід
        action.playerMove()
    }
}