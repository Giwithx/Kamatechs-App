package com.example.kamatechsmobileapplication.login

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
import com.example.kamatechsmobileapplication.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )
        (activity as AppCompatActivity).supportActionBar?.title = "Login"
        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.myLoginViewModel = loginViewModel

        binding.lifecycleOwner = this

        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigatetoRegister()
                loginViewModel.doneNavigatingRegister()
            }
        })

        loginViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername .observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })
        loginViewModel.navigatetoUserDetails.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                navigatetoHome()
                loginViewModel.doneNavigatingHome()
            }
        })

        return binding.root
    }
    private fun navigatetoHome(){
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
    private fun navigatetoRegister(){
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

}