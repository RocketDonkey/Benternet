package com.rocketdonkey.benternet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rocketdonkey.benternet.R
import com.rocketdonkey.benternet.databinding.FragmentLightsBinding
import com.rocketdonkey.benternet.network.BenternetApi
import com.rocketdonkey.benternet.network.Commands

class LightsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLightsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_lights, container, false
        )

        // Set up the click listeners for the macro buttons.
        // TODO: Push the API calls into a ViewModel.

        binding.btnUpstairsLightsOn.setOnClickListener {
            BenternetApi.sendCommands(Commands.Lights.upstairsLightsOn())
        }

        binding.btnUpstairsLightsOff.setOnClickListener {
            BenternetApi.sendCommands(Commands.Lights.upstairsLightsOff())
        }

        binding.btnDownstairsLightsOn.setOnClickListener {
            BenternetApi.sendCommands(Commands.Lights.downstairsLightsOn())
        }

        binding.btnDownstairsLightsOff.setOnClickListener {
            BenternetApi.sendCommands(Commands.Lights.downstairsLightsOff())
        }

        return binding.root
    }
}