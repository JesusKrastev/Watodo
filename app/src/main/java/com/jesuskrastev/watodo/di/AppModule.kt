package com.jesuskrastev.watodo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }
}