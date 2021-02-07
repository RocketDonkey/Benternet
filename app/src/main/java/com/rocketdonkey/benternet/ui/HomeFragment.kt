package com.rocketdonkey.benternet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rocketdonkey.benternet.R
import com.rocketdonkey.benternet.databinding.FragmentHomeBinding
import com.rocketdonkey.benternet.network.BenternetApi
import com.rocketdonkey.benternet.network.Commands

/**
 * Home screen fragment.
 *
 * This presents a few of the most commonly used 'macro' options.
 */
class HomeFragment : Fragment() {
    private val TAG: String? = HomeFragment::class.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        // Set up the click listeners for the macro buttons.
        // TODO: Push the API calls into a ViewModel.

        binding.btnSetMood.setOnClickListener {
            BenternetApi.sendCommands(Commands.setTheMood())
        }

        binding.btnTimeForBed.setOnClickListener {
            BenternetApi.sendCommands(Commands.timeForBed())
        }

        binding.btnGimmeDaLight.setOnClickListener {
            BenternetApi.sendCommands(Commands.gimmeDaLight())
        }

        binding.btnLightsOut.setOnClickListener {
            BenternetApi.sendCommands(Commands.lightsOut())
        }

        return binding.root
    }
}