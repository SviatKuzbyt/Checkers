package ua.sviatkuzbyt.checkers.ui.game.elements

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ua.sviatkuzbyt.checkers.R

class CellView: View {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    private var type = EMPTY_WHITE

    init {
        setBackground()
    }

    fun setType(type: Int){
        this.type = type
        setBackground()
        invalidate()
    }

    private fun setBackground(){
        when(type){
            EMPTY_WHITE -> setBackgroundColor(ContextCompat.getColor(context, R.color.blue_white))
            EMPTY_BLACK -> setBackgroundColor(ContextCompat.getColor(context, R.color.blue_black))
            MOVE -> setBackgroundResource(R.drawable.cell_move)
            WHITE_CHECKER -> setBackgroundResource(R.drawable.cell_whie)
            BLACK_CHECKER -> setBackgroundResource(R.drawable.cell_black)
            WHITE_QUEEN -> setBackgroundResource(R.drawable.cell_white_queen)
            BLACK_QUEEN -> setBackgroundResource(R.drawable.cell_black_queen)
        }
    }

    fun getType() = type

    companion object{
        const val EMPTY_WHITE = 0
        const val EMPTY_BLACK = 1
        const val MOVE = 2
        const val WHITE_CHECKER = 3
        const val BLACK_CHECKER = 4
        const val WHITE_QUEEN = 5
        const val BLACK_QUEEN = 6
    }
}