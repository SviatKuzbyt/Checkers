package ua.sviatkuzbyt.checkers.ui.game.elements

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import ua.sviatkuzbyt.checkers.R
import ua.sviatkuzbyt.checkers.data.elements.CellData
import ua.sviatkuzbyt.checkers.ui.game.CellAction

@SuppressLint("ViewConstructor")
class CellView(
    context: Context,
    private val data: CellData,
    private var action: CellAction
) : View(context) {

    init {
        setBackground()
        setClick()
    }

    private fun setClick(){
        setOnClickListener {
            when(data.type){
                WHITE_CHECKER -> action.step(data.id, WHITE_PLAYER, -9, -11)
                BLACK_CHECKER -> action.step(data.id, BLACK_PLAYER, 9, 11)
                MOVE -> action.move(data.id)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    fun setType(type: Int){
        data.type = type
        setBackground()
        invalidate()
    }

    private fun setBackground(){
        when(data.type){
            EMPTY -> setBackgroundResource(R.drawable.cell_black_empty)
            MOVE -> setBackgroundResource(R.drawable.cell_move)
            WHITE_CHECKER -> setBackgroundResource(R.drawable.cell_whie)
            BLACK_CHECKER -> setBackgroundResource(R.drawable.cell_black)
            WHITE_QUEEN -> setBackgroundResource(R.drawable.cell_white_queen)
            BLACK_QUEEN -> setBackgroundResource(R.drawable.cell_black_queen)
        }
    }

    fun getType() = data.type

    companion object{
        const val EMPTY = 1
        const val MOVE = 2
        const val WHITE_CHECKER = 3
        const val BLACK_CHECKER = 4
        const val WHITE_QUEEN = 5
        const val BLACK_QUEEN = 6
        const val WHITE_PLAYER = 7
        const val BLACK_PLAYER = 8
    }
}