package com.example.worldwanderer.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnFocusChangeListener
import android.view.View.OnKeyListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity(), OnClickListener, OnFocusChangeListener, OnKeyListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.usernameEt.onFocusChangeListener = this
        binding.emailEt.onFocusChangeListener = this
        binding.passwordEt.onFocusChangeListener = this
        binding.confirmPasswordEt.onFocusChangeListener = this
    }

    private fun validateEmail(): Boolean{
        var errorMessage: String? = ""
        val emailInput: String = binding.emailEt.text.toString()

        if(emailInput.isEmpty()){
            errorMessage = "Email is required!"
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            errorMessage = "Invalid email address! "
        }

        printErrorIfPresent(errorMessage, binding.emailTil)

        return errorMessage == ""
    }

    private fun validateUsername(): Boolean{
        var errorMessage: String? = ""
        val usernameInput: String = binding.usernameEt.text.toString()

        if(usernameInput.isEmpty()){
            errorMessage = "Username is required!"
        }

        printErrorIfPresent(errorMessage, binding.usernameTil)

        return errorMessage == ""
    }

    private fun validatePassword(): Boolean{
        var errorMessage: String? = ""
        val passwordInput: String = binding.passwordEt.text.toString()

        if(passwordInput.isEmpty()){
            errorMessage = "Password is required!"
        }

        if (passwordInput.length < 6){
            errorMessage = "Password needs to be at least 6 characters long!"
        }

        printErrorIfPresent(errorMessage, binding.passwordTil)

        return errorMessage == ""
    }

    private fun validatePasswordMatching(): Boolean{
        var errorMessage: String? = ""
        val passwordInput: String = binding.passwordEt.text.toString()
        val confirmPasswordInput: String = binding.confirmPasswordEt.text.toString()

        if(passwordInput != confirmPasswordInput){
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


    override fun onClick(view: View?) {

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(view != null){
            when(view.id){
                R.id.emailEt -> {

                    if (hasFocus) {
                        if(binding.emailTil.isErrorEnabled){
                            binding.emailTil.isErrorEnabled = false
                        }
                    }

                    if(validateEmail()){
                        binding.emailTil.apply {
                            setStartIconDrawable(R.drawable.baseline_check_box_24)
                            setStartIconTintList(ColorStateList.valueOf(ResourcesCompat.getColor(
                                resources, R.color.dark_green, null)))
                        }
                    }

                }

                R.id.usernameEt -> {

                    if (hasFocus) {
                        if(binding.usernameTil.isErrorEnabled){
                            binding.usernameTil.isErrorEnabled = false
                        }
                    }

                    if(validateUsername()){
                        binding.usernameTil.apply {
                            setStartIconDrawable(R.drawable.baseline_check_box_24)
                            setStartIconTintList(ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.dark_green, null)))
                        }
                    }

                }

                R.id.passwordEt -> {

                    if (hasFocus) {
                        if(binding.passwordTil.isErrorEnabled){
                            binding.passwordTil.isErrorEnabled = false
                        }
                    }

                    if(validatePassword() && binding.confirmPasswordEt.text!!.isEmpty() && validatePasswordMatching()){
                        binding.passwordTil.apply {
                            setStartIconDrawable(R.drawable.baseline_check_box_24)
                            setStartIconTintList(ColorStateList.valueOf(ResourcesCompat.getColor(
                                resources, R.color.dark_green, null)))
                        }
                    }

                }

                R.id.confirmPasswordEt -> {

                    if(hasFocus) {
                        if(binding.confirmPasswordTil.isErrorEnabled){
                            binding.confirmPasswordTil.isErrorEnabled = false
                        }
                    }

                    if(validatePasswordMatching() && binding.passwordEt.text.toString().isNotEmpty()){
                        binding.confirmPasswordTil.apply {
                            setStartIconDrawable(R.drawable.baseline_check_box_24)
                            setStartIconTintList(ColorStateList.valueOf(ResourcesCompat.getColor(
                                resources, R.color.dark_green, null)))
                        }
                    }


                }
            }
        }
    }

    override fun onKey(view: View?, event: Int, keyevent: KeyEvent?): Boolean {
        return false
    }
}