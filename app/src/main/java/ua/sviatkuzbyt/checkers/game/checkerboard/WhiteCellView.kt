package ua.sviatkuzbyt.checkers.game.checkerboard

import android.content.Context
import android.view.View
import ua.sviatkuzbyt.checkers.R

class WhiteCellView(context: Context) : View(context) {

    init {
        setBackgroundResource(R.drawable.cell_white_empty)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}