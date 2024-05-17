package com.emm.bingo.features.auth.data

import io.github.jan.supabase.gotrue.user.UserInfo

interface UserRepository {

    suspend fun login(email: String, password: String): UserInfo?
}