package com.rocketdonkey.benternet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rocketdonkey.benternet.R
import com.rocketdonkey.benternet.databinding.FragmentTvBinding

class TvFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTvBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tv, container, false
        )

        return binding.root
    }
}