package droid.maxaria.maxander.primenumbers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import droid.maxaria.maxander.primenumbers.R
import droid.maxaria.maxander.primenumbers.databinding.FragmentChooseLevelBinding
import droid.maxaria.maxander.primenumbers.domain.entity.Level
import droid.maxaria.maxander.primenumbers.presentation.gamefragment.GameFragment

class ChooseLevelFragment : Fragment() {
    private var _binding:FragmentChooseLevelBinding? = null
    private val binding:FragmentChooseLevelBinding
        get() = _binding ?:throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentChooseLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLevelTest.setOnClickListener{
            launchNextFragment(Level.TEST)
        }
        binding.buttonLevelHard.setOnClickListener{
            launchNextFragment(Level.HARD)
        }
        binding.buttonLevelEasy.setOnClickListener{
            launchNextFragment(Level.EASY)
        }
        binding.buttonLevelNormal.setOnClickListener{
            launchNextFragment(Level.NORMAL)
        }
    }

    private fun launchNextFragment(level: Level){
        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
