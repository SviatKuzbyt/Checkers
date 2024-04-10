package ua.sviatkuzbyt.checkers.ui.game

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import ua.sviatkuzbyt.checkers.databinding.ActivityGameBinding
import ua.sviatkuzbyt.checkers.ui.game.elements.CellView
import ua.sviatkuzbyt.checkers.ui.game.elements.WhiteCellView

class GameActivity : AppCompatActivity(), CellAction {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameList: List<CellView>
    private var whiteCount = 12
    private var blackCount = 12
    private var movesPosition = mutableListOf<Int>()
    private var targetPosition = -1
    private var firstPlayer = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameList = listOf(

            CellView(this, CellView.BLACK_CHECKER, 12, this),
            CellView(this, CellView.BLACK_CHECKER, 14, this),
            CellView(this, CellView.BLACK_CHECKER, 16, this),
            CellView(this, CellView.BLACK_CHECKER, 18, this),

            CellView(this, CellView.BLACK_CHECKER, 21, this),
            CellView(this, CellView.BLACK_CHECKER, 23, this),
            CellView(this, CellView.BLACK_CHECKER, 25, this),
            CellView(this, CellView.BLACK_CHECKER, 27, this),

            CellView(this, CellView.BLACK_CHECKER, 32, this),
            CellView(this, CellView.BLACK_CHECKER, 34, this),
            CellView(this, CellView.BLACK_CHECKER, 36, this),
            CellView(this, CellView.BLACK_CHECKER, 38, this),

            CellView(this, CellView.EMPTY_BLACK, 41, this),
            CellView(this, CellView.EMPTY_BLACK, 43, this),
            CellView(this, CellView.EMPTY_BLACK, 45, this),
            CellView(this, CellView.EMPTY_BLACK, 47, this),

            CellView(this, CellView.EMPTY_BLACK, 52, this),
            CellView(this, CellView.EMPTY_BLACK, 54, this),
            CellView(this, CellView.EMPTY_BLACK, 56, this),
            CellView(this, CellView.EMPTY_BLACK, 58, this),

            CellView(this, CellView.WHITE_CHECKER, 61, this),
            CellView(this, CellView.WHITE_CHECKER, 63, this),
            CellView(this, CellView.WHITE_CHECKER, 65, this),
            CellView(this, CellView.WHITE_CHECKER, 67, this),

            CellView(this, CellView.WHITE_CHECKER, 72, this),
            CellView(this, CellView.WHITE_CHECKER, 74, this),
            CellView(this, CellView.WHITE_CHECKER, 76, this),
            CellView(this, CellView.WHITE_CHECKER, 78, this),

            CellView(this, CellView.WHITE_CHECKER, 81, this),
            CellView(this, CellView.WHITE_CHECKER, 83, this),
            CellView(this, CellView.WHITE_CHECKER, 85, this),
            CellView(this, CellView.WHITE_CHECKER, 87, this),
        )

        createCheckersBoard(gameList)
    }

//    private fun createCheckersBoard(list: List<CellView>) {
//        var position = 0
//        for (row in 1..8){
//            val tableRow = TableRow(this)
//            for(column in 1..8){
//                tableRow.addView(list[position])
//                position++
//            }
//            binding.checkerboard.addView(tableRow)
//        }
//    }

    private fun createCheckersBoard(list: List<CellView>){
        var position = 0
        var firstWhite = true
        for(row in 1..8){
            val tableRow = TableRow(this)
            for (column in 1..4){
                if (firstWhite){
                    tableRow.addView(WhiteCellView(this))
                    tableRow.addView(list[position])
                } else{
                    tableRow.addView(list[position])
                    tableRow.addView(WhiteCellView(this))
                }
                position++
            }
            binding.checkerboard.addView(tableRow)
            firstWhite = !firstWhite
        }
    }

    private fun rotateGameToolBar(rotate: Float, gravity: Int) {
        binding.gameToolBar.apply {
            rotation = rotate
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                gravity
            )
        }
    }

    override fun whiteStep(id: Int) {
        clearMoves()
        targetPosition = gameList.indexOfFirst { it.cellId == id}
        if (targetPosition != -1 && firstPlayer){
            move(id - 9)
            move(id - 11)
        }
    }

    private fun clearMoves(){
        movesPosition.forEach {
            if(gameList[it].getType() == CellView.MOVE){
                gameList[it].setType(CellView.EMPTY_BLACK)
            }
        }
        movesPosition.clear()
    }

    private fun move(id: Int){
        gameList.indexOfFirst { it.cellId == id}.run {
            if (this != -1 && gameList[this].getType() == CellView.EMPTY_BLACK){
                gameList[this].setType(CellView.MOVE)
                movesPosition.add(this)
            }
        }
    }

    override fun blackStep(id: Int) {
        clearMoves()
        targetPosition = gameList.indexOfFirst { it.cellId == id}
        if (targetPosition != -1 && !firstPlayer){
            move(id + 9)
            move(id + 11)
        }

    }

    override fun setMove(id: Int) {
        if (targetPosition != -1){
            gameList[targetPosition].setType(CellView.EMPTY_BLACK)
            targetPosition = -1
        }

        gameList.indexOfFirst { it.cellId == id}.run {
            if (this != -1){
                gameList[this].setType(
                    if(firstPlayer) CellView.WHITE_CHECKER
                    else CellView.BLACK_CHECKER
                )
                firstPlayer = !firstPlayer
            }
        }

        clearMoves()

    }
}