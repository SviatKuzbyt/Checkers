package ua.sviatkuzbyt.checkers.ui.game

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ua.sviatkuzbyt.checkers.data.SaveGameFileManager
import ua.sviatkuzbyt.checkers.databinding.ActivityGameBinding
import ua.sviatkuzbyt.checkers.ui.isResumeGame
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGameBinding.inflate(layoutInflater)
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

        binding.tempText.text = text

        binding.tempText.setOnClickListener {
            file.delete()
            isResumeGame = false
            Toast.makeText(this, "Видалено", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatDate(milliseconds: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        return sdf.format(calendar.time)
    }
}