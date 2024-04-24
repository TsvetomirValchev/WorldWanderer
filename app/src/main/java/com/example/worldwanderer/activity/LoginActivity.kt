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
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    // View binding and Firebase authentication instance
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    // Views and input fields
    private lateinit var clickToRegister: TextView
    private lateinit var emailOrUsernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // Initialize Firebase authentication
        auth = Firebase.auth

        // Initialize views and input fields
        clickToRegister = findViewById(R.id.click_to_register)
        emailOrUsernameInput = findViewById(R.id.usernameOrEmailEt)
        passwordInput = findViewById(R.id.passwordEt)

        // Set click listeners for buttons
        binding.loginBtn.setOnClickListener(this)
        binding.clickToRegister.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is already signed in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Redirect to main activity if user is already signed in
            val changeToApp = Intent(applicationContext, MainActivity::class.java)
            startActivity(changeToApp)
            finish()
        }
    }

    override fun onClick(view: View?) {
        // Handle click events for buttons
        when (view!!.id) {
            R.id.loginBtn -> {
                // Login button clicked
                if (TextUtils.isEmpty(emailOrUsernameInput.text.toString())) {
                    // Check if email/username field is empty
                    Toast.makeText(
                        baseContext,
                        "Email or username field cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (TextUtils.isEmpty(passwordInput.text.toString())) {
                    // Check if password field is empty
                    Toast.makeText(
                        baseContext,
                        "Password cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                // Sign in with email and password
                auth.signInWithEmailAndPassword(
                    emailOrUsernameInput.text.toString(),
                    passwordInput.text.toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login successful
                        Toast.makeText(
                            baseContext,
                            "Login successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        val changeToApp = Intent(applicationContext, MainActivity::class.java)
                        startActivity(changeToApp)
                        finish()
                    } else {
                        // Login failed
                        Toast.makeText(
                            baseContext,
                            "Authentication failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            R.id.click_to_register -> {
                // Click to register link clicked
                val changeToRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(changeToRegister)
                finish()
            }
        }
    }
}