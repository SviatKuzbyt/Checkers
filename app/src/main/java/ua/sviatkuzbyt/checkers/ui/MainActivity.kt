package ua.sviatkuzbyt.checkers.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import ua.sviatkuzbyt.checkers.data.SaveGameFileManager
import ua.sviatkuzbyt.checkers.databinding.ActivityMainBinding
import ua.sviatkuzbyt.checkers.ui.game.GameActivity

var isResumeGame: Boolean? = null

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Встановлення UI
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Чи є збережена гра
        if (isResumeGame == null){
            isResumeGame = SaveGameFileManager(this).isExist()
        }

        //Встановлення функціоналу кнопок
        binding.newGameButton.setOnClickListener {
            openGameActivity(false)
        }
        binding.resumeGameButton.setOnClickListener {
            openGameActivity(true)
        }
    }

    private fun openGameActivity(resumeGame: Boolean){
        val intent = Intent(this, GameActivity::class.java)
        if (resumeGame) intent.putExtra("rg", true)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        //Встановлення кнопки "Відновлення гри"
        binding.resumeGameButton.also {
            if (isResumeGame == true && it.isGone)
                it.visibility = View.VISIBLE
            else if(isResumeGame == false && it.isVisible)
                it.visibility = View.GONE
        }
    }
}