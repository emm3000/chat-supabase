package com.emm.bingo.features.auth.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultUserRepository(
    private val supabase: SupabaseClient,
) : UserRepository {

    override suspend fun login(email: String, password: String): UserInfo? = withContext(Dispatchers.IO) {
        val user: UserInfo? = supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        return@withContext user
    }
}