package droid.maxaria.maxander.primenumbers.data

import droid.maxaria.maxander.primenumbers.domain.Repository
import droid.maxaria.maxander.primenumbers.domain.entity.GameSettings
import droid.maxaria.maxander.primenumbers.domain.entity.Level
import droid.maxaria.maxander.primenumbers.domain.entity.Question
import java.lang.RuntimeException
import kotlin.math.min
import kotlin.math.max
import kotlin.random.Random

object RepositoryImpl: Repository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1
    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val firstNum = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - firstNum
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue - 1, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum = sum, visibleNumber = firstNum, options = options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level){
            Level.TEST-> GameSettings(10,3,50,8)
            Level.EASY-> GameSettings(10,10,70,60)
            Level.NORMAL-> GameSettings(20,20,80,40)
            Level.HARD-> GameSettings(30,30,90,40)
        }
    }
}