package com.example.hobbyapps.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.hobbyapps.R
import com.example.hobbyapps.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        var USERNAME = "USERNAME"
//        var FIRSTNAME = "FIRSTNAME"
//        var LASTNAME = "LASTNAME"
        var sharedFile = "com.example.hobbyapps"

        var shared: SharedPreferences = this.requireActivity().getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE )


        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtFirstname = view.findViewById<TextView>(R.id.txtFirstname)
        val txtLastname = view.findViewById<TextView>(R.id.txtLastname)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnChange = view.findViewById<Button>(R.id.btnChange)

        var name = shared.getString(LoginActivity.USERNAME,"").toString()
        var firstName = shared.getString(LoginActivity.FIRSTNAME,"").toString()
        var lastName = shared.getString(LoginActivity.LASTNAME,"").toString()

//        var name = shared.getString(USERNAME,"").toString()
//        var firstName = shared.getString(FIRSTNAME,"").toString()
//        var lastName = shared.getString(LASTNAME,"").toString()

        txtName.text = "Name : " + name
        txtFirstname.text = "First Name : " + firstName
        txtLastname.text = "Last Name : " + lastName

        btnLogout.setOnClickListener{
            var editor: SharedPreferences.Editor = shared.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

            // Finish the current activity to prevent navigating back to it
//            finish()

        }
        btnChange.setOnClickListener{
            val action = ProfileFragmentDirections.actionChangePassword()
            Navigation.findNavController(it).navigate(action)
        }
    }
}