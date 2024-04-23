package ua.sviatkuzbyt.checkers.game.activity

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ua.sviatkuzbyt.checkers.game.elements.GameAction
import ua.sviatkuzbyt.checkers.game.window.GetUpWindow
import ua.sviatkuzbyt.checkers.R
import ua.sviatkuzbyt.checkers.game.window.WinWindow
import ua.sviatkuzbyt.checkers.game.checkerboard.CellView
import ua.sviatkuzbyt.checkers.game.checkerboard.Checkerboard
import ua.sviatkuzbyt.checkers.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity(), GameAction {

    private lateinit var binding: ActivityGameBinding
    private lateinit var viewModel: GameViewModel

    //Запуск діяльності, прив'язування інтерфейу до коду та завантаження даних
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()
        setCheckerboard()

        binding.giveUpButton.setOnClickListener {
            getUp()
        }
        binding.closeGameButton.setOnClickListener {
            finish()
        }
    }

    private fun setViewModel(){
        //Встановлення ViewModel
        viewModel = ViewModelProvider(
            this,
            GameViewModel.factory(
                application,
                intent.getBooleanExtra("loadFile", false)
            )
        )[GameViewModel::class.java]

        //спостереження за помилками та кінцем гри і відповідно оновлення інтерфейсу
        viewModel.message.observe(this){
            when(it){
                GameViewModel.FINISH ->
                    finish()
                GameViewModel.ERROR_READ ->
                    Toast.makeText(this, R.string.error_read, Toast.LENGTH_LONG).show()
                GameViewModel.ERROR_SAVE ->
                    Toast.makeText(this, R.string.error_save, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setCheckerboard(){
        viewModel.dataLiveData.observe(this){
            Checkerboard(binding.checkerboard, it, this, this)
            if (it.currentPlayer == CellView.BLACK_PLAYER)
                rotateGameToolBar(180f, Gravity.BOTTOM, R.string.playerTwo)
        }
    }

    private fun rotateGameToolBar(rotate: Float, gravity: Int, text: Int) {
        binding.gameToolBar.apply {
            rotation = rotate
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                gravity
            )
        }
        binding.playerText.setText(text)
    }

    private fun getUp(){
        val player =
            if (viewModel.getPlayer() == CellView.WHITE_PLAYER)
                CellView.BLACK_PLAYER
            else CellView.WHITE_PLAYER

        GetUpWindow({ makeWin(player) }, this).showWindow()
    }

    private fun makeWin(player: Int){
        val text =
            if (player == CellView.WHITE_PLAYER) R.string.win_one
            else R.string.win_two
        WinWindow( text, {viewModel.finishGame()}, this).showWindow()
    }

    //Збереження гри у файл
    override fun onPause() {
        super.onPause()
        viewModel.saveData()
    }

    override fun playerMove() {
        //перевірка чи гравець не переміг, оновлення інтерфейсу та гравя
        viewModel.dataLiveData.value?.let {
            if (it.currentPlayer == CellView.WHITE_PLAYER){
                if (it.blackCount == 0) makeWin(it.currentPlayer)
                rotateGameToolBar(180f, Gravity.BOTTOM, R.string.playerTwo)
            } else{
                if (it.whiteCount == 0) makeWin(it.currentPlayer)
                rotateGameToolBar(0f, Gravity.TOP, R.string.playerOne)
            }

            it.currentPlayer =
                if (it.currentPlayer == CellView.BLACK_PLAYER) CellView.WHITE_PLAYER
                else CellView.BLACK_PLAYER
        }
    }
}