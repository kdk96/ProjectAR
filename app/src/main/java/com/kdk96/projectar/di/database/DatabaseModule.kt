package com.kdk96.projectar.di.database

import android.arch.persistence.room.Room
import android.content.Context
import com.kdk96.database.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(
            context,
            Database::class.java,
            "projectar_database.db"
    ).build()
}