package com.emm.bingo.features.auth.di

import com.emm.bingo.features.auth.data.DefaultUserRepository
import com.emm.bingo.features.auth.data.UserRepository
import com.emm.bingo.features.auth.domain.UserAuthentication
import com.emm.bingo.features.auth.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val loginModule = module {
    factory<UserRepository> { DefaultUserRepository(get()) }
    singleOf(::UserAuthentication)
    viewModelOf(::LoginViewModel)
}