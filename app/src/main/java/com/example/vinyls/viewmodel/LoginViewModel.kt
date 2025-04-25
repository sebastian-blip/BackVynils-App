package com.example.vinyls.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class LoginViewModel : ViewModel() {

    // Estado simulado de login (por ejemplo, podría ser validación de datos)
    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun login() {
        // Aquí iría la lógica real de validación (API, etc.)
        _isLoggedIn.value = true
    }

    fun logout() {
        _isLoggedIn.value = false
    }
}
