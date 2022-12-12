package com.example.kamatechsmobileapplication.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
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
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homeFragment -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.aboutFragment -> {
                    replaceFragment(AboutFragment())
                    true
                }
                R.id.FAQFragment -> {
                    replaceFragment(FAQFragment())
                    true
                }
                else -> false
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
    private  fun replaceFragment(fragment: Fragment){
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeLayout,fragment)
        fragmentTransaction.addToBackStack(null).commit()
    }


}