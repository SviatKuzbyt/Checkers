package ua.sviatkuzbyt.checkers.ui.game

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ua.sviatkuzbyt.checkers.data.SaveGameFileManager
import ua.sviatkuzbyt.checkers.databinding.ActivityGameBinding
import ua.sviatkuzbyt.checkers.ui.isResumeGame
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val file = SaveGameFileManager(this)
        val text = if(intent.getBooleanExtra("rg", false)){
            file.open()
        } else {
            val tempText = formatDate(System.currentTimeMillis())
            file.write(tempText)
            isResumeGame = true
            tempText
        }

    }

    private fun rotateGameToolBar(rotate: Float, gravity: Int) {
        binding.gameToolBar.apply {
            rotation = rotate
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                gravity
            )
        }
    }
    private fun formatDate(milliseconds: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        return sdf.format(calendar.time)
    }
}