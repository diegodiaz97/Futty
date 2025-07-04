package com.diego.futty.authentication.login.presentation.viewmodel

import androidx.compose.runtime.State
import com.diego.futty.home.design.presentation.component.banner.Banner

interface LoginViewContract {
    val email: State<String>
    val password: State<String>
    val banner: State<Banner?>
    val canLogin: State<Boolean>
    val hideKeyboard: State<Boolean>
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun onLoginClicked()
    fun onSignupClicked()
    fun onRecoveryClicked()
    fun onLoginWithGoogleClicked()
    fun onLoginWithAppleClicked()
    fun hideKeyboard()
}
