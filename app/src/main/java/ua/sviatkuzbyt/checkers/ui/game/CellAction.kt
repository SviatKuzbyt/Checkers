package ua.sviatkuzbyt.checkers.ui.game

interface CellAction {
    fun move(id: Int)
    fun step(id: Int, type: Int, pushSizeOne: Int, pushSizeTwo: Int)
}