package com.emm.bingo.features.shared.di

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val sharedModule = module {
    single<SupabaseClient> { supabase() }
}

private fun supabase(): SupabaseClient {
    return createSupabaseClient(
        supabaseUrl = "https://zizswlyqllqnfmqouvcy.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InppenN3bHlxbGxxbmZtcW91dmN5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU5MDE4OTYsImV4cCI6MjAzMTQ3Nzg5Nn0.WR3MbhfYiiWzF7ap4Zw3plWJyAstPkszfq-6C3BWkPw"
    ) {
        install(Auth)
        defaultSerializer = KotlinXSerializer(Json { ignoreUnknownKeys = true })
    }
}