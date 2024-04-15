package ua.sviatkuzbyt.checkers.ui.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ua.sviatkuzbyt.checkers.data.elements.CellData
import ua.sviatkuzbyt.checkers.data.elements.GameData
import ua.sviatkuzbyt.checkers.databinding.ActivityGameBinding
import ua.sviatkuzbyt.checkers.ui.game.elements.CellView
import ua.sviatkuzbyt.checkers.ui.game.elements.Checkerboard

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
//    private lateinit var gameList: List<CellView>
//    private var whiteCount = 12
//    private var blackCount = 12
//    private var movesPosition = mutableListOf<Int>()
//    private var targetPosition = -1
//    private var player = CellView.WHITE_CHECKER
//    private val takeCandidates = mutableMapOf<Int, MutableList<Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = GameData(
            CellView.WHITE_PLAYER,
            12,
            12,
            listOf(
                CellData(CellView.BLACK_CHECKER, 12),
                CellData(CellView.BLACK_CHECKER, 14),
                CellData(CellView.BLACK_CHECKER, 16),
                CellData(CellView.BLACK_CHECKER, 18),

                CellData(CellView.BLACK_CHECKER, 21),
                CellData(CellView.BLACK_CHECKER, 23),
                CellData(CellView.BLACK_CHECKER, 25),
                CellData(CellView.BLACK_CHECKER, 27),

                CellData(CellView.BLACK_CHECKER, 32),
                CellData(CellView.BLACK_CHECKER, 34),
                CellData(CellView.BLACK_CHECKER, 36),
                CellData(CellView.BLACK_CHECKER, 38),

                CellData(CellView.EMPTY, 41),
                CellData(CellView.EMPTY, 43),
                CellData(CellView.EMPTY, 45),
                CellData(CellView.EMPTY, 47),

                CellData(CellView.EMPTY, 52),
                CellData(CellView.EMPTY, 54),
                CellData(CellView.EMPTY, 56),
                CellData(CellView.EMPTY, 58),

                CellData(CellView.WHITE_CHECKER, 61),
                CellData(CellView.WHITE_CHECKER, 63),
                CellData(CellView.WHITE_CHECKER, 65),
                CellData(CellView.WHITE_CHECKER, 67),

                CellData(CellView.WHITE_CHECKER, 72),
                CellData(CellView.WHITE_CHECKER, 74),
                CellData(CellView.WHITE_CHECKER, 76),
                CellData(CellView.WHITE_CHECKER, 78),

                CellData(CellView.WHITE_CHECKER, 81),
                CellData(CellView.WHITE_CHECKER, 83),
                CellData(CellView.WHITE_CHECKER, 85),
                CellData(CellView.WHITE_CHECKER, 87),
            )
        )

        Checkerboard(
            binding.checkerboard,
            data,
            this
        )

    }

//    private fun createCheckersBoard(list: List<CellView>){
//        var position = 0
//        var firstWhite = true
//        for(row in 1..8){
//            val tableRow = TableRow(this)
//            for (column in 1..4){
//                if (firstWhite){
//                    tableRow.addView(WhiteCellView(this))
//                    tableRow.addView(list[position])
//                } else{
//                    tableRow.addView(list[position])
//                    tableRow.addView(WhiteCellView(this))
//                }
//                position++
//            }
//            binding.checkerboard.addView(tableRow)
//            firstWhite = !firstWhite
//        }
//    }
//    private fun rotateGameToolBar() {
//        val rotate = if (player == CellView.WHITE_CHECKER) 0f else 180f
//        val gravity = if(player == CellView.WHITE_CHECKER) Gravity.TOP else Gravity.BOTTOM
//        val text = if (player == CellView.WHITE_CHECKER) R.string.playerOne else R.string.playerTwo
//
//        binding.gameToolBar.apply {
//            rotation = rotate
//            layoutParams = FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                gravity
//            )
//        }
//        binding.playerText.setText(text)
//    }

//    override fun whiteStep(id: Int) {
//        clearMoves()
//        targetPosition = gameList.indexOfFirst { it.cellId == id}
//        if (targetPosition != -1 && player == CellView.WHITE_CHECKER){
//            moveTake(id, CellView.BLACK_CHECKER)
//            move(id, - 9)
//            move(id, - 11)
//        }
//    }

//    private fun moveTake(id: Int, checker: Int, list: MutableList<Int> = mutableListOf()){
//        checkChecker(id, 11, checker, list.toMutableList())
//        checkChecker(id, 9, checker, list.toMutableList())
//        checkChecker(id, -11, checker, list.toMutableList())
//        checkChecker(id, -9, checker, list.toMutableList())
//    }

//    private fun checkChecker(id: Int, step: Int, checker: Int, list: MutableList<Int>){
//        gameList.indexOfFirst { it.cellId == id + step}.let { it ->
//            if (it != -1 && gameList[it].getType() == checker){
//                gameList.indexOfFirst { it.cellId == id + step + step}.let{ it2 ->
//                    if (it2 != -1 && gameList[it2].getType() == CellView.EMPTY){
//                        movesPosition.add(it2)
//                        gameList[it2].setType(CellView.MOVE)
//                        list.add(it)
//                        takeCandidates[it2] = list
//
//                        moveTake(id + step + step, checker, list)
//                    }
//                }
//            }
//        }
//    }
//    private fun clearMoves(){
//        movesPosition.forEach {
//            if(gameList[it].getType() == CellView.MOVE){
//                gameList[it].setType(CellView.EMPTY)
//            }
//        }
//        movesPosition.clear()
//        takeCandidates.clear()
//    }

//    private fun move(id: Int, addStep: Int){
//        gameList.indexOfFirst { it.cellId == id + addStep}.run {
//            if (this != -1){
//                if (gameList[this].getType() == CellView.EMPTY){
//                    gameList[this].setType(CellView.MOVE)
//                    movesPosition.add(this)
//                }
//            }
//        }
//    }
//
//    override fun blackStep(id: Int) {
//        clearMoves()
//        targetPosition = gameList.indexOfFirst { it.cellId == id}
//        if (targetPosition != -1 && player == CellView.BLACK_CHECKER){
//            moveTake(id, CellView.WHITE_CHECKER)
//            move(id, 9)
//            move(id,11)
//        }
//    }
//
//    override fun setMove(id: Int) {
//        if (targetPosition != -1){
//            gameList[targetPosition].setType(CellView.EMPTY)
//            targetPosition = -1
//        }
//
//        gameList.indexOfFirst { it.cellId == id}.run {
//            if (this != -1){
//                gameList[this].setType(player)
//            }
//
//            takeCandidates[this]?.let {
//                it.forEach { n ->
//                    gameList[n].setType(CellView.EMPTY)
//                    if (player == CellView.WHITE_CHECKER)
//                        blackCount --
//                    else whiteCount --
//                    checkWin()
//                }
//            }
//        }
//
//        player =
//            if (player == CellView.WHITE_CHECKER) CellView.BLACK_CHECKER
//            else CellView.WHITE_CHECKER
//        rotateGameToolBar()
//        clearMoves()
//
//    }
//
//    fun checkWin(){}
}

