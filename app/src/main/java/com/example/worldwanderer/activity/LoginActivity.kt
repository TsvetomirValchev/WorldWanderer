package com.example.worldwanderer.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.worldwanderer.MainActivity
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), OnClickListener{


    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var clickToRegister: TextView
    private lateinit var emailOrUsernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        auth = Firebase.auth
        clickToRegister = findViewById(R.id.click_to_register)
        emailOrUsernameInput = findViewById(R.id.usernameOrEmailEt)
        passwordInput = findViewById(R.id.passwordEt)
        binding.loginBtn.setOnClickListener(this)
        binding.clickToRegister.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            val changeToApp = Intent(applicationContext, MainActivity::class.java)
            startActivity(changeToApp)
            finish()
        }
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.loginBtn -> {

                if (TextUtils.isEmpty(emailOrUsernameInput.text.toString())){
                    Toast.makeText(
                        baseContext,
                        "Email or username field cannot be empty",
                        Toast.LENGTH_SHORT,
                    ).show()
                    return
                }

                if (TextUtils.isEmpty(passwordInput.text.toString())){
                    Toast.makeText(
                        baseContext,
                        "Password cannot be empty",
                        Toast.LENGTH_SHORT,
                    ).show()
                    return
                }

                auth.signInWithEmailAndPassword(emailOrUsernameInput.text.toString(), passwordInput.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Login successful",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val changeToApp = Intent(applicationContext, MainActivity::class.java)
                            startActivity(changeToApp)
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }

            R.id.click_to_register -> {
                val changeToRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(changeToRegister)
                finish()
            }
        }
    }

}