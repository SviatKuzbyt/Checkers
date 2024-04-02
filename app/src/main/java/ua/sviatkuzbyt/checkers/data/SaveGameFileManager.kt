package ua.sviatkuzbyt.checkers.data

import android.content.Context
import java.io.File

class SaveGameFileManager(context: Context) {
    private val file = File(context.filesDir, "saveGame.txt")

    fun open() = file.readText()

    fun write(text: String) = file.writeText(text)

    fun delete(){
        file.delete()
    }

    fun isExist() = file.exists()

}