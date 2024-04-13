package com.example.hobbyapps.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hobbyapps.R
import com.example.hobbyapps.databinding.ActivityLoginBinding
import com.example.hobbyapps.databinding.ActivityRegistBinding
import com.example.hobbyapps.model.User
import com.example.hobbyapps.viewmodel.userViewModel

class RegistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistBinding
    private lateinit var viewModel: userViewModel
    private lateinit var shared: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    var id : Int = 0
    var username : String = ""
    var password : String = ""
    var firstname : String = ""
    var lastname : String = ""

    companion object{
        const val ID = "ID"
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
        const val FIRSTNAME = "FIRSTNAME"
        const val LASTNAME = "LASTNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var sharedFile = "com.example.hobbyapps"
        shared = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)

        viewModel = ViewModelProvider(this).get(userViewModel::class.java)

        binding.btnRegis.setOnClickListener {
            val username = binding.txtUser.editText?.text.toString()
            val firstname = binding.txtNamaDepan.editText?.text.toString()
            val lastname = binding.txtNamaBelakang.editText?.text.toString()
            val password = binding.txtPass.editText?.text.toString()
            val passConfirm = binding.txtPassConfirm.editText?.text.toString()

            if (username.isEmpty() || password.isEmpty() || passConfirm.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.regist(username, passConfirm, firstname, lastname)
                observeViewModel()
            }
        }
    }
    private fun observeViewModel() {
        viewModel.userLD.observe(this, Observer { user ->
            if (user != null) {
                id = user.id?.toInt() ?: -1
                password = user.password.toString()
                username = user.username.toString()
                firstname = user.firstname.toString()
                lastname = user.lastname.toString()

                // Menyimpan data pengguna di SharedPreferences
                editor = shared.edit()
                editor.putInt(ID, id)
                editor.putString(USERNAME, username)
                editor.putString(PASSWORD, password)
                editor.putString(FIRSTNAME, firstname)
                editor.putString(LASTNAME, lastname)
                editor.apply()

                // Dialihkan ke MainActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                // Menampilkan pesan kesalahan jika gagal masuk
                Toast.makeText(applicationContext, "Registration Failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }

}