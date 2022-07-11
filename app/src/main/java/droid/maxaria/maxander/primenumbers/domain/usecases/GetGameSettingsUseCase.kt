package droid.maxaria.maxander.primenumbers.domain.usecases

import droid.maxaria.maxander.primenumbers.domain.Repository
import droid.maxaria.maxander.primenumbers.domain.entity.GameSettings
import droid.maxaria.maxander.primenumbers.domain.entity.Level

class GetGameSettingsUseCase(private val repository: Repository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}