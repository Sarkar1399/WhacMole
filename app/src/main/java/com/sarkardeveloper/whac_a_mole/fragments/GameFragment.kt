package com.sarkardeveloper.whac_a_mole.fragments

import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.commit
import com.sarkardeveloper.whac_a_mole.R
import com.sarkardeveloper.whac_a_mole.databinding.FragmentGameBinding
import kotlin.random.Random

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var btnImageList: List<ImageButton>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        btnImageList = listOf(
            binding.btnMolePart1,
            binding.btnMolePart2,
            binding.btnMolePart3,
            binding.btnMolePart4,
            binding.btnMolePart5,
            binding.btnMolePart6,
            binding.btnMolePart7,
            binding.btnMolePart8,
            binding.btnMolePart9
        )

        var visibleMole = 0
        var scoreText = 0

        for (button in btnImageList) {
            button.setOnClickListener {
                if (btnImageList[visibleMole].isEnabled && btnImageList[visibleMole] == it) {
                    scoreText += 1
                    binding.txtCurrentScore.text = "Ваш текуший счет: $scoreText"
                    btnImageList[visibleMole].isEnabled = false
                    btnImageList[visibleMole].setImageResource(R.drawable.mole_part_02)
                }
            }
        }

        object : CountDownTimer(30000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                btnImageList[visibleMole].isEnabled = false
                btnImageList[visibleMole].setImageResource(R.drawable.mole_part_01)
                val activity = activity
                var newMole = Random.nextInt(9)
                while (newMole == visibleMole) newMole = Random.nextInt(5)
                visibleMole = newMole
                if (activity != null && isAdded) {
                    binding.txtTime.text = "Время: ${millisUntilFinished / 1000}"
                    showNewMole(mole = visibleMole)
                }
            }

            override fun onFinish() {
                val endGameFragment = ResultGameFragment.newInstant(scoreText)
                val activity = activity
                if (activity != null && isAdded) {
                    requireActivity().supportFragmentManager.commit {
                        replace(R.id.containerViews, endGameFragment)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                }
            }
        }.start()




        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showNewMole(mole: Int) {
        val btn = btnImageList[mole]
        btn.isEnabled = true
        val layers = arrayOfNulls<Drawable>(2)

        layers[0] = ResourcesCompat.getDrawable(resources, R.drawable.mole_part_03, null)
        layers[1] = ResourcesCompat.getDrawable(resources, R.drawable.mole_part_04, null)

        val transition = TransitionDrawable(layers)
        transition.isCrossFadeEnabled = true
        btn.setImageDrawable(transition)
        transition.startTransition(180)
    }

}