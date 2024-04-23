package ua.sviatkuzbyt.checkers.game.elements

data class CellData(
    var type: Int,
    val id: Int
)

data class GameData(
    var currentPlayer: Int,
    var whiteCount: Int,
    var blackCount: Int,
    val cells: List<CellData>
)