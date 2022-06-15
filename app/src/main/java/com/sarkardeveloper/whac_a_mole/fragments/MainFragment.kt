package com.sarkardeveloper.whac_a_mole.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.sarkardeveloper.whac_a_mole.Constants
import com.sarkardeveloper.whac_a_mole.R
import com.sarkardeveloper.whac_a_mole.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        sharedPreferences =
            requireContext().getSharedPreferences(Constants.sharedPreferences, Context.MODE_PRIVATE)

        binding.tvRecord.text =
            getString(
                R.string.userRecord,
                sharedPreferences.getInt(Constants.recordScore, 0).toString()
            )

        binding.btnPlay.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace<GameFragment>(R.id.containerViews)
                addToBackStack("MainFragmentTag")

            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}