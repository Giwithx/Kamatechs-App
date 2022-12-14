package com.example.kamatechsmobileapplication.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.kamatechsmobileapplication.R
import com.example.kamatechsmobileapplication.database.RegisterDatabase
import com.example.kamatechsmobileapplication.database.RegisterRepository
import com.example.kamatechsmobileapplication.databinding.RegisterHomeFragmentBinding
import com.google.android.material.snackbar.Snackbar


class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: RegisterHomeFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_home_fragment, container, false
        )
        (activity as AppCompatActivity).supportActionBar?.title = "Register"
        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.myViewModel = registerViewModel

        binding.lifecycleOwner = this

        registerViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigatetoLogin()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG",it.toString()+"000000000000000000000000")
        })


        registerViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Snackbar.make(binding.myCoordinatorLayout,"Please fill all fields", Snackbar.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        })

        registerViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Snackbar.make(binding.myCoordinatorLayout,"Username already taken.", Snackbar.LENGTH_SHORT).show()
                registerViewModel.donetoastUserName()
            }
        })

        return binding.root
    }

    private fun navigatetoLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}