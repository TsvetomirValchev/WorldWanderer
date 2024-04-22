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
import com.example.worldwanderer.MainActivity
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity(), OnClickListener, OnFocusChangeListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var clickToLogin: TextView


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            val changeToApp = Intent(applicationContext, MainActivity::class.java)
            startActivity(changeToApp)
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        clickToLogin = findViewById(R.id.click_to_login)
        binding.registerBtn.setOnClickListener(this)
        binding.clickToLogin.setOnClickListener(this)
        binding.emailEt.onFocusChangeListener = this
        binding.passwordEt.onFocusChangeListener = this
        binding.confirmPasswordEt.onFocusChangeListener = this
    }

    override fun onClick(view: View?) {

        val email: String = binding.emailEt.text.toString()
        val password: String = binding.passwordEt.text.toString();

        when (view!!.id) {
            R.id.registerBtn -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (isValidRegistration() && task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Account created.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val changeToLogin = Intent(applicationContext, LoginActivity::class.java)
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
                val changeToLogin = Intent(applicationContext, LoginActivity::class.java)
                startActivity(changeToLogin)
                finish()
            }
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.emailEt -> {

                    if (hasFocus) {
                        binding.emailTil.isErrorEnabled = false
                    }

                    if (validateEmail()) {
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

                    if (hasFocus) {
                        binding.passwordTil.isErrorEnabled = false
                    }

                    if (validatePassword() && binding.confirmPasswordEt.text!!.isEmpty() && validatePasswordMatching()) {
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

                    if (hasFocus) {
                        binding.confirmPasswordTil.isErrorEnabled = false
                    }

                    if (validatePasswordMatching() && binding.passwordEt.text.toString()
                            .isNotEmpty()
                    ) {
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
        var errorMessage: String? = ""
        val passwordInput: String = binding.passwordEt.text.toString()
        val confirmPasswordInput: String = binding.confirmPasswordEt.text.toString()

        if (passwordInput != confirmPasswordInput) {
            errorMessage = "The passwords does not match!"
        }

        printErrorIfPresent(errorMessage, binding.confirmPasswordTil)

        return errorMessage == ""
    }

    private fun printErrorIfPresent(errorMessage: String?, field: TextInputLayout) {
        if (errorMessage != "") {
            field.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
    }

    private fun isValidRegistration(): Boolean {
        var isValid = true

        if (!validateEmail()) isValid = false
        if (!validatePassword()) isValid = false
        if (!validatePasswordMatching()) isValid = false

        return isValid
    }
}