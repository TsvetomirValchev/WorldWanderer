package com.example.worldwanderer.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnFocusChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity(), OnClickListener, OnFocusChangeListener {

    // View binding and Firebase authentication instance
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var clickToLogin: TextView

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // Initialize Firebase authentication
        auth = FirebaseAuth.getInstance()

        // Initialize views and set listeners
        clickToLogin = findViewById(R.id.click_to_login)
        binding.registerBtn.setOnClickListener(this)
        binding.clickToLogin.setOnClickListener(this)
        binding.emailEt.onFocusChangeListener = this
        binding.passwordEt.onFocusChangeListener = this
        binding.confirmPasswordEt.onFocusChangeListener = this
    }

    override fun onClick(view: View?) {
        // Handle click events for buttons
        val email: String = binding.emailEt.text.toString()
        val password: String = binding.passwordEt.text.toString()

        when (view!!.id) {
            R.id.registerBtn -> {
                // Register button clicked
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        // Check if registration is valid and successful
                        if (isValidRegistration() && task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Account created.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val changeToLogin =
                                Intent(applicationContext, LoginActivity::class.java)
                            startActivity(changeToLogin)
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

            R.id.click_to_login -> {
                // Click to login link clicked
                val changeToLogin = Intent(applicationContext, LoginActivity::class.java)
                startActivity(changeToLogin)
                finish()
            }
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        // Handle focus changes for input fields
        if (view != null) {
            when (view.id) {
                R.id.emailEt -> {
                    // Email input field
                    if (hasFocus) {
                        binding.emailTil.isErrorEnabled = false
                    }

                    if (validateEmail()) {
                        // Set email input field icon if valid
                        binding.emailTil.apply {
                            setStartIconDrawable(R.drawable.baseline_check_box_24)
                            setStartIconTintList(
                                ColorStateList.valueOf(
                                    ResourcesCompat.getColor(
                                        resources, R.color.dark_green, null
                                    )
                                )
                            )
                        }
                    }
                }

                R.id.passwordEt -> {
                    // Password input field
                    if (hasFocus) {
                        binding.passwordTil.isErrorEnabled = false
                    }

                    if (validatePassword() && binding.confirmPasswordEt.text!!.isEmpty() && validatePasswordMatching()) {
                        // Set password input field icon if valid
                        binding.passwordTil.apply {
                            setStartIconDrawable(R.drawable.baseline_check_box_24)
                            setStartIconTintList(
                                ColorStateList.valueOf(
                                    ResourcesCompat.getColor(
                                        resources, R.color.dark_green, null
                                    )
                                )
                            )
                        }
                    }
                }

                R.id.confirmPasswordEt -> {
                    // Confirm password input field
                    if (hasFocus) {
                        binding.confirmPasswordTil.isErrorEnabled = false
                    }

                    if (validatePasswordMatching() && binding.passwordEt.text.toString().isNotEmpty()) {
                        // Set confirm password input field icon if valid
                        binding.confirmPasswordTil.apply {
                            setStartIconDrawable(R.drawable.baseline_check_box_24)
                            setStartIconTintList(
                                ColorStateList.valueOf(
                                    ResourcesCompat.getColor(
                                        resources, R.color.dark_green, null
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun validateEmail(): Boolean {
        // Validate email format
        var errorMessage: String? = ""
        val emailInput: String = binding.emailEt.text.toString()

        if (emailInput.isEmpty()) {
            errorMessage = "Email is required!"
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            errorMessage = "Invalid email address! "
        }

        printErrorIfPresent(errorMessage, binding.emailTil)

        return errorMessage == ""
    }

    private fun validatePassword(): Boolean {
        // Validate password length
        var errorMessage: String? = ""
        val passwordInput: String = binding.passwordEt.text.toString()

        if (passwordInput.isEmpty()) {
            errorMessage = "Password is required!"
        }

        if (passwordInput.length < 6) {
            errorMessage = "Password needs to be at least 6 characters long!"
        }

        printErrorIfPresent(errorMessage, binding.passwordTil)

        return errorMessage == ""
    }

    private fun validatePasswordMatching(): Boolean {
        // Validate password confirmation
        var errorMessage: String? = ""
        val passwordInput: String = binding.passwordEt.text.toString()
        val confirmPasswordInput: String = binding.confirmPasswordEt.text.toString()

        if (passwordInput != confirmPasswordInput) {
            errorMessage = "The passwords do not match!"
        }

        printErrorIfPresent(errorMessage, binding.confirmPasswordTil)

        return errorMessage == ""
    }

    private fun printErrorIfPresent(errorMessage: String?, field: TextInputLayout) {
        // Print error message if present
        if (errorMessage != "") {
            field.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
    }

    private fun isValidRegistration(): Boolean {
        // Check if registration input is valid
        var isValid = true

        if (!validateEmail()) isValid = false
        if (!validatePassword()) isValid = false
        if (!validatePasswordMatching()) isValid = false

        return isValid
    }
}