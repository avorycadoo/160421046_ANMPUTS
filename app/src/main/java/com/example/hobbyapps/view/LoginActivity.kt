package com.example.hobbyapps.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hobbyapps.R
import com.example.hobbyapps.databinding.ActivityLoginBinding
import com.example.hobbyapps.viewmodel.userViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: userViewModel
    private lateinit var shared: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    var id : Int = 0
    var username : String = ""
    var password : String = ""
    var firstname : String = ""
    var lastname : String = ""


    companion object{
        val ID = "ID"
        val USERNAME = "USERNAME"
        val PASSWORD = "PASSWORD"
        val FIRSTNAME = "FIRSTNAME"
        val LASTNAME = "LASTNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var sharedFile = "com.example.hobbyapps"
        shared = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)

        editor= shared.edit()
//        var checkLogin = shared.getInt("ID", -1)
//        if (checkLogin != -1) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            this.finish()
//        }

        viewModel = ViewModelProvider(this).get(userViewModel::class.java)


        binding.btnLogin.setOnClickListener {
            username = binding.txtUsername.editText?.text.toString()
            password = binding.txtPassword.editText?.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                viewModel.login(username, password)


            } else {
                Toast.makeText(
                    this,
                    "Username and Password must not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnCreate.setOnClickListener {
            val intent = Intent(this,RegistActivity::class.java)
            startActivity(intent)

        }
        observeViewModel()

    }
    private fun observeViewModel() {
        viewModel.userLD.observe(this, Observer { user ->
            Log.d("user", user.toString())
            if (user != null) {
                Log.d("username", user.toString())

                id = user.id?.toInt() ?: -1
                username = user.username.toString()
                password = user.password.toString()
                firstname = user.firstname.toString()
                lastname = user.lastname.toString()
                Log.d("username", username)

                editor = shared.edit()
                editor.putInt(ID, id)
                editor.putString(USERNAME, username)
                editor.putString(PASSWORD, password)
                editor.putString(FIRSTNAME, firstname)
                editor.putString(LASTNAME, lastname)
                editor.apply()


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(applicationContext, "Your Username or Password is Incorrect", Toast.LENGTH_SHORT)
                    .show()

            }


        })
    }
    }