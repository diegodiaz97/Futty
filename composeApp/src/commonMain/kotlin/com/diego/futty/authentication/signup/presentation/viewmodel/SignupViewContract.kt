package com.diego.futty.authentication.signup.presentation.viewmodel

import androidx.compose.runtime.State
import com.diego.futty.design.presentation.component.banner.Banner

interface SignupViewContract {
    val hideKeyboard: State<Boolean>
    val email: State<String>
    val password: State<String>
    val confirmPassword: State<String>
    val banner: State<Banner?>
    val canCreateAccount: State<Boolean>
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun onSignupClicked()
    fun hideKeyboard()
    fun updateConfirmPassword(password: String)
}
