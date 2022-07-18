package droid.maxaria.maxander.primenumbers.presentation.gamefragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import droid.maxaria.maxander.primenumbers.domain.entity.Level

class GameViewModelFactory(
    private val application: Application,
    private val level: Level,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameFragmentViewModel::class.java)) {
            return GameFragmentViewModel(application, level) as T
        }
        throw RuntimeException("Unknown ViewModel $modelClass")
    }
}