package droid.maxaria.maxander.primenumbers.presentation.gamefragment

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import droid.maxaria.maxander.primenumbers.R
import droid.maxaria.maxander.primenumbers.data.RepositoryImpl
import droid.maxaria.maxander.primenumbers.domain.entity.GameResult
import droid.maxaria.maxander.primenumbers.domain.entity.GameSettings
import droid.maxaria.maxander.primenumbers.domain.entity.Level
import droid.maxaria.maxander.primenumbers.domain.entity.Question
import droid.maxaria.maxander.primenumbers.domain.usecases.GenerateQuestionUseCase
import droid.maxaria.maxander.primenumbers.domain.usecases.GetGameSettingsUseCase

class GameFragmentViewModel(
    private val application:Application,
    private val level:Level
) : ViewModel() {
    private val repository = RepositoryImpl
    private val generateQuestionUseCase: GenerateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase: GetGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _percentRightAnswers = MutableLiveData<Int>()
    val percentRightAnswers: LiveData<Int>
        get() = _percentRightAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private lateinit var gameSettings: GameSettings
    private var timer: CountDownTimer? = null
    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    init {
        startGame()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun getGameSettings() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun updateProgress(){
        _percentRightAnswers.value = calculateProgress()
        _progressAnswers.value = String.format(
            application.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        Log.d("TAG",_progressAnswers.value.toString())
        Log.d("TAG",progressAnswers.value.toString())
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percentRightAnswers.value!! >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculateProgress():Int {
        return if (countOfQuestions == 0)
            0
        else ((countOfRightAnswers/countOfQuestions.toDouble())*100).toInt()
    }
    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND,
            MILLIS_IN_SECOND
        ) {
            override fun onTick(p0: Long) {
                _formattedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millis: Long): String {
        val seconds = millis / MILLIS_IN_SECOND
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds % SECONDS_IN_MINUTES
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = (enoughCount.value ==true) and (enoughPercent.value==true),
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions = countOfQuestions,
            gameSettings = gameSettings
        )
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}