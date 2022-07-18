package droid.maxaria.maxander.primenumbers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import droid.maxaria.maxander.primenumbers.R
import droid.maxaria.maxander.primenumbers.databinding.FragmentGameFinishedBinding
import droid.maxaria.maxander.primenumbers.domain.entity.GameResult
import droid.maxaria.maxander.primenumbers.presentation.gamefragment.GameFragment
import java.lang.RuntimeException


class GameFinishedFragment : Fragment() {
    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        bindViews()
    }

    private fun bindViews() {
        binding.apply {
            tvRequiredAnswers.text = formatText(
                getString(R.string.required_score),
                args.gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            tvScoreAnswers.text = formatText(
                getString(R.string.score_answers),
                args.gameResult.countOfRightAnswers.toString()
            )
            tvRequiredPercentage.text = formatText(
                getString(R.string.required_percentage),
                args.gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )
            tvScorePercentage.text = formatText(
                getString(R.string.score_percentage),
                ((args.gameResult.countOfRightAnswers /
                        args.gameResult.countOfQuestions.toDouble()) * 100)
                    .toInt().toString() + "%"
            )

            emojiResult.setImageResource(
                if (args.gameResult.winner) R.drawable.ic_smile
                else R.drawable.ic_sad
            )
        }
    }

    private fun formatText(resString: String, value: String): String {
        return String.format(
            resString,
            value
        )
    }



    private fun retryGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
