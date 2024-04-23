package ua.sviatkuzbyt.checkers.game.window

import android.content.Context
import androidx.appcompat.app.AlertDialog
import ua.sviatkuzbyt.checkers.R

class WinWindow(text: Int, action: () -> Unit, context: Context) {
    private val builder: AlertDialog.Builder = AlertDialog.Builder(context, R.style.GameWindow)
    private var dialog: AlertDialog

    //встановлення тексту та дії у вікні
    init {
        builder
            .setTitle(text)
            .setPositiveButton(R.string.ok) { _, _ ->
                action.invoke()
            }
            .setCancelable(false)

        dialog = builder.create()
    }

    fun showWindow(){ dialog.show() }
}