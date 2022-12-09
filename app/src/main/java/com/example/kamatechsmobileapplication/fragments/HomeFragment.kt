package com.example.kamatechsmobileapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.kamatechsmobileapplication.R
import com.example.kamatechsmobileapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)

        binding.btnWeather.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_weatherActivity)
        }
        binding.btnUsers.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_userDetailsFragment)
        }
        binding.btnStorage.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_storageFragment)
        }
        return binding.root
    }


}