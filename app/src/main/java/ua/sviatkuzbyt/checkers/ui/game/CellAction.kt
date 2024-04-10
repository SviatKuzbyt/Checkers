package ua.sviatkuzbyt.checkers.ui.game

interface CellAction {
    fun whiteStep(id: Int)
    fun blackStep(id: Int)
    fun setMove(id: Int)
}