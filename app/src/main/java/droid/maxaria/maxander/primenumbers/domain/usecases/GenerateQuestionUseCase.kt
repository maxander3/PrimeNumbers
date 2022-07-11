package droid.maxaria.maxander.primenumbers.domain.usecases

import droid.maxaria.maxander.primenumbers.domain.Repository
import droid.maxaria.maxander.primenumbers.domain.entity.Question

class GenerateQuestionUseCase(private val repository: Repository) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}