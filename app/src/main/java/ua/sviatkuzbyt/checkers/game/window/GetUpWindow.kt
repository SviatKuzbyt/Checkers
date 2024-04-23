package ua.sviatkuzbyt.checkers.game.window

import android.content.Context
import androidx.appcompat.app.AlertDialog
import ua.sviatkuzbyt.checkers.R

class GetUpWindow(action: () -> Unit, context: Context) {
    private val builder: AlertDialog.Builder = AlertDialog.Builder(context, R.style.GameWindow)
    private var dialog: AlertDialog

    //встановлення тексту та дії у вікні
    init {
        builder
            .setTitle(R.string.give_up)
            .setPositiveButton(R.string.yes) { _, _ ->
                action.invoke()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }

        dialog = builder.create()
    }

    fun showWindow(){ dialog.show() }
}