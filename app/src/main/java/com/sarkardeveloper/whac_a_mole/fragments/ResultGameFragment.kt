package com.sarkardeveloper.whac_a_mole.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.sarkardeveloper.whac_a_mole.Constants
import com.sarkardeveloper.whac_a_mole.R
import com.sarkardeveloper.whac_a_mole.databinding.FragmentResultGameBinding

class ResultGameFragment : Fragment() {

    private var _binding: FragmentResultGameBinding? = null
    private val binding get() = _binding!!
    private var currentScore: Int? = null

    companion object {
        fun newInstant(currentScore: Int) = ResultGameFragment().apply {
            arguments = Bundle().apply {
                putInt(Constants.userScoreValue, currentScore)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentScore = it.getInt(Constants.userScoreValue)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultGameBinding.inflate(inflater, container, false)

        val sharedPref =
            requireContext().getSharedPreferences(Constants.sharedPreferences, Context.MODE_PRIVATE)
        val recordScore = sharedPref.getInt(Constants.recordScore, 0)

        binding.txtCurrentScore.text = getString(R.string.userScore, currentScore.toString())

        if (currentScore != null) {
            if (currentScore!! > recordScore) {
                binding.txtRecord.text = getString(R.string.userNewRecord, currentScore.toString())
                sharedPref.edit().putInt(Constants.recordScore, currentScore!!).apply()
            } else {
                binding.txtRecord.text = getString(R.string.userRecord, recordScore.toString())
            }
        }
        binding.btnAgain.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace<GameFragment>(R.id.containerViews)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        binding.btnMain.setOnClickListener {
            goToMainScreen()

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goToMainScreen()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun goToMainScreen() {
        requireActivity().supportFragmentManager
            .popBackStack("MainFragmentTag", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}