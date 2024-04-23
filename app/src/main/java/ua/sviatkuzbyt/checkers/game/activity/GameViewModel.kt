package ua.sviatkuzbyt.checkers.game.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.sviatkuzbyt.checkers.game.elements.GameData
import ua.sviatkuzbyt.checkers.SaveGameFileManager
import ua.sviatkuzbyt.checkers.isResumeGame

class GameViewModel(application: Application, private val loadFile: Boolean): AndroidViewModel(application) {
    private lateinit var data: GameData
    val dataLiveData = MutableLiveData<GameData>()
    val message = MutableLiveData<Int>()
    private val fileManager = SaveGameFileManager(application)

    init { loadData() }

    //при запуску завантаження збереженої гри або нової
    private fun loadData() = viewModelScope.launch(Dispatchers.IO){
        if (loadFile){
            try {
                data = fileManager.getSavedData()
            } catch (_: Exception){
                data = fileManager.getNewData()
                message.postValue(ERROR_READ)
            }
        } else data = fileManager.getNewData()

        dataLiveData.postValue(data)
    }

    fun saveData(){
        if (message.value != FINISH){
            isResumeGame = true
            viewModelScope.launch(Dispatchers.IO){
                try {
                    fileManager.saveData(data)
                } catch (_: Exception){
                    message.postValue(ERROR_SAVE)
                }
            }
        }
    }

    fun finishGame(){
        isResumeGame = false
        viewModelScope.launch(Dispatchers.IO){
            fileManager.deleteFile()
            message.postValue(FINISH)
        }
    }

    fun getPlayer() =
        if (::data.isInitialized) data.currentPlayer
        else -1

    companion object{
        fun factory(
            application: Application, loadFile: Boolean
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer { GameViewModel(application, loadFile) }
        }

        const val FINISH = 0
        const val ERROR_SAVE = 1
        const val ERROR_READ = 2
    }
}