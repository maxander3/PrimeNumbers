package droid.maxaria.maxander.primenumbers.presentation.gamefragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import droid.maxaria.maxander.primenumbers.R
import droid.maxaria.maxander.primenumbers.databinding.FragmentGameBinding
import droid.maxaria.maxander.primenumbers.domain.entity.GameResult
import droid.maxaria.maxander.primenumbers.domain.entity.Level
import droid.maxaria.maxander.primenumbers.presentation.GameFinishedFragment
import java.lang.RuntimeException


class GameFragment : Fragment() {
    private val args by navArgs<GameFragmentArgs>()

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")
    private val viewModel by lazy {
        ViewModelProvider(this,
            GameViewModelFactory(requireActivity().application, args.level)
        )[GameFragmentViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeViewModel()
        initCLicks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.apply {
                tvOption1.text = it.options[0].toString()
                tvOption2.text = it.options[1].toString()
                tvOption3.text = it.options[2].toString()
                tvOption4.text = it.options[3].toString()
                tvOption5.text = it.options[4].toString()
                tvOption6.text = it.options[5].toString()
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()
            }
        }
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchNextFragment(it)
        }
        viewModel.percentRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughCount.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner) {
            binding.progressBar.progressTintList = ColorStateList.valueOf(getColorByState(it))
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun launchNextFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

    private fun initCLicks() {
        binding.tvOption1.setOnClickListener {
            viewModel.chooseAnswer(viewModel.question.value?.options?.get(0)
                ?: throw RuntimeException("Level question error"))
        }
        binding.tvOption2.setOnClickListener {
            viewModel.chooseAnswer(viewModel.question.value?.options?.get(1)
                ?: throw RuntimeException("Level question error"))
        }
        binding.tvOption3.setOnClickListener {
            viewModel.chooseAnswer(viewModel.question.value?.options?.get(2)
                ?: throw RuntimeException("Level question error"))
        }
        binding.tvOption4.setOnClickListener {
            viewModel.chooseAnswer(viewModel.question.value?.options?.get(3)
                ?: throw RuntimeException("Level question error"))
        }
        binding.tvOption5.setOnClickListener {
            viewModel.chooseAnswer(viewModel.question.value?.options?.get(4)
                ?: throw RuntimeException("Level question error"))
        }
        binding.tvOption6.setOnClickListener {
            viewModel.chooseAnswer(viewModel.question.value?.options?.get(5)
                ?: throw RuntimeException("Level question error"))
        }
    }


}
