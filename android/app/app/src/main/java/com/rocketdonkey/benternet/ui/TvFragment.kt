package com.rocketdonkey.benternet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rocketdonkey.benternet.R
import com.rocketdonkey.benternet.databinding.FragmentTvBinding
import com.rocketdonkey.benternet.network.BenternetApi
import com.rocketdonkey.benternet.network.Commands

class TvFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTvBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tv, container, false
        )

        // Set up the click listeners for the macro buttons.
        // TODO: Push the API calls into a ViewModel.

        binding.btnTvPower.setOnClickListener {
            BenternetApi.sendCommands(Commands.Tv.togglePower())
        }

        binding.btnTvVolUp.setOnClickListener {
            BenternetApi.sendCommands(Commands.Tv.volumeUp())
        }

        binding.btnTvVolDown.setOnClickListener {
            BenternetApi.sendCommands(Commands.Tv.volumeDown())
        }

        binding.btnTvMute.setOnClickListener {
            BenternetApi.sendCommands(Commands.Tv.mute())
        }

        return binding.root
    }
}