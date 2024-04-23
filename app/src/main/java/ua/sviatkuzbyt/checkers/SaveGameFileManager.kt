package ua.sviatkuzbyt.checkers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ua.sviatkuzbyt.checkers.game.checkerboard.CellView
import ua.sviatkuzbyt.checkers.game.elements.CellData
import ua.sviatkuzbyt.checkers.game.elements.GameData
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Type

class SaveGameFileManager(context: Context) {
    private val file = File(context.filesDir, "saveGame.txt")
    private val gson = Gson()

    fun getSavedData(): GameData =
        FileReader(file).use { reader ->
            val type: Type = object : TypeToken<GameData>() {}.type
            gson.fromJson(reader, type)
        }

    fun getNewData() = GameData(
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

    fun saveData(data: GameData){
        val dataString = gson.toJson(data)

        FileWriter(file).use {
            it.write(dataString)
        }
    }

    fun deleteFile(){
        file.delete()
    }

    fun isExists() = file.exists()
}