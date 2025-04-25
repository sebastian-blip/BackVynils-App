package com.example.vinyls.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("vinyls_prefs", Context.MODE_PRIVATE)

    fun setLogin(value: Boolean) {
        prefs.edit().putBoolean("is_logged_in", value).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
