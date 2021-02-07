package com.rocketdonkey.benternet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rocketdonkey.benternet.R
import com.rocketdonkey.benternet.databinding.FragmentFireplaceBinding

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

        return binding.root
    }
}
