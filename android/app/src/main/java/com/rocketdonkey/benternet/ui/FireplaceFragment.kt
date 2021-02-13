package com.rocketdonkey.benternet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rocketdonkey.benternet.R
import com.rocketdonkey.benternet.databinding.FragmentFireplaceBinding
import com.rocketdonkey.benternet.network.BenternetApi
import com.rocketdonkey.benternet.network.Commands

/**
 * Fireplace controls fragment.
 *
 * Presents options for controlling the fireplace.
 */
class FireplaceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout.
        val binding: FragmentFireplaceBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_fireplace, container, false
            )

        // Set up the click listeners for the macro buttons.
        // TODO: Push the API calls into a ViewModel.

        binding.btnFireplacePower.setOnClickListener {
            BenternetApi.sendCommands(Commands.Fireplace.togglePower())
        }

        binding.btnFireplaceHeat.setOnClickListener {
            BenternetApi.sendCommands(Commands.Fireplace.toggleHeat())
        }

        binding.btnFireplaceLights.setOnClickListener {
            BenternetApi.sendCommands(Commands.Fireplace.toggleLights())
        }

        return binding.root
    }
}
