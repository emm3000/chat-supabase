package com.emm.bingo.features.auth.domain

import com.emm.bingo.features.auth.data.UserRepository
import io.github.jan.supabase.gotrue.user.UserInfo

class UserAuthentication(private val repository: UserRepository) {

    suspend fun authenticate(uiEmail: String, uiPassword: String): UserInfo? {
        val email = Email(uiEmail)
        val password = Password(uiPassword)
        return repository.login(email.value, password.value)
    }
}

@JvmInline
value class Email(val value: String) {

    init {
        require(validate()) {
            "Ingresa un email correcto"
        }
    }

    private fun validate(): Boolean {
        val emailRegex: Regex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
        return value.matches(emailRegex)
    }
}

@JvmInline
value class Password(val value: String) {

    init {
        require(validate()) {
            "Tu contraseña debe contener al menos 8 caracteres"
        }
    }

    private fun validate(): Boolean {
        return value.length > 7
    }
}