package ua.sviatkuzbyt.checkers.game.elements

interface CellAction {
    fun move(id: Int)
    fun step(id: Int, type: Int, pushSizeOne: Int, pushSizeTwo: Int)
    fun stepQueen(id: Int, type: Int)
}

interface GameAction{
    fun playerMove()
}