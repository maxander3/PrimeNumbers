package droid.maxaria.maxander.primenumbers.domain

import droid.maxaria.maxander.primenumbers.domain.entity.GameSettings
import droid.maxaria.maxander.primenumbers.domain.entity.Level
import droid.maxaria.maxander.primenumbers.domain.entity.Question

interface Repository {
    fun generateQuestion(
        maxSumValue:Int,
        countOfOptions:Int
    ):Question
    fun getGameSettings(level: Level):GameSettings
}