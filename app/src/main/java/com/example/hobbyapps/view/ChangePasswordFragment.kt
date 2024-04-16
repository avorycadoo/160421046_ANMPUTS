package com.example.hobbyapps.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hobbyapps.R
import com.example.hobbyapps.databinding.FragmentChangePasswordBinding
import com.example.hobbyapps.databinding.FragmentProfileBinding
import com.example.hobbyapps.viewmodel.passwordViewModel
import com.example.hobbyapps.viewmodel.userViewModel


class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var viewModel: passwordViewModel
    private lateinit var shared: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    var password : String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedFile = "com.example.hobbyapps"

        var shared: SharedPreferences = this.requireActivity().getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE )

        viewModel = ViewModelProvider(this).get(passwordViewModel::class.java)



        binding.btnChangePassword.setOnClickListener{
            password = binding.txtPass.editText?.text.toString()
            var id = shared.getInt(LoginActivity.ID,-1).toString()

            if (password.isNotBlank()) {
                viewModel.password(password,id)
                observeViewModel()
                val action = ChangePasswordFragmentDirections.actionProfile()
                Navigation.findNavController(it).navigate(action)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Username and Password must not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun observeViewModel() {
        viewModel.passLD.observe(viewLifecycleOwner, Observer { User ->
            if (User != null) {
                password = User.password.toString()

                editor = shared.edit()
                editor.putString(LoginActivity.PASSWORD, password)
                editor.apply()


            } else {
                Toast.makeText(
                    requireContext(),
                    "Wrong Password",
                    Toast.LENGTH_SHORT
                )
                    .show()

                // Hapus data yang ada di SharedPreferences
                editor = shared.edit()
                editor.clear()
                editor.apply()
            }

        })

    }

}