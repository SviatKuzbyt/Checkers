package ua.sviatkuzbyt.checkers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import ua.sviatkuzbyt.checkers.databinding.ActivityMainBinding
import ua.sviatkuzbyt.checkers.game.activity.GameActivity

var isResumeGame: Boolean? = null

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Запуск діяльності і прив'язування інтерфейу до коду
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newGameButton.setOnClickListener {
            openGameActivity(false)
        }
        binding.resumeGameButton.setOnClickListener {
            openGameActivity(true)
        }
    }

    private fun openGameActivity(resumeGame: Boolean){
        val intent = Intent(this, GameActivity::class.java)
        if (resumeGame) intent.putExtra("loadFile", true)
        startActivity(intent)
    }

    //Перевірка наявності збереженої гри при запуску і поверненні до діяльності
    override fun onResume() {
        super.onResume()
        binding.resumeGameButton.apply {
            when(isResumeGame){
                null -> checkSaveGame()
                true -> if(isGone) visibility = View.VISIBLE
                false -> if(isVisible) visibility = View.GONE
            }
        }
    }

    private fun checkSaveGame(){
        if (SaveGameFileManager(this).isExists()){
            binding.resumeGameButton.visibility = View.VISIBLE
            isResumeGame = true
        } else isResumeGame = false
    }
}