package ua.sviatkuzbyt.checkers.ui.game.elements

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import ua.sviatkuzbyt.checkers.R
import ua.sviatkuzbyt.checkers.ui.game.CellAction

@SuppressLint("ViewConstructor")
class CellView(
    context: Context,
    private var type: Int,
    var cellId: Int,
    private var action: CellAction
) : View(context) {

    init {
        setBackground()
        setClick()
    }

    private fun setClick(){
        setOnClickListener {
            when(type){
                WHITE_CHECKER -> action.whiteStep(cellId)
                BLACK_CHECKER -> action.blackStep(cellId)
                MOVE -> action.setMove(cellId)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    fun setType(type: Int){
        this.type = type
        setBackground()
        invalidate()
    }

    private fun setBackground(){
        when(type){
            EMPTY -> setBackgroundResource(R.drawable.cell_black_empty)
            MOVE -> setBackgroundResource(R.drawable.cell_move)
            WHITE_CHECKER -> setBackgroundResource(R.drawable.cell_whie)
            BLACK_CHECKER -> setBackgroundResource(R.drawable.cell_black)
            WHITE_QUEEN -> setBackgroundResource(R.drawable.cell_white_queen)
            BLACK_QUEEN -> setBackgroundResource(R.drawable.cell_black_queen)
        }
    }

    fun getType() = type

    companion object{
        const val EMPTY = 1
        const val MOVE = 2
        const val WHITE_CHECKER = 3
        const val BLACK_CHECKER = 4
        const val WHITE_QUEEN = 5
        const val BLACK_QUEEN = 6
    }
}