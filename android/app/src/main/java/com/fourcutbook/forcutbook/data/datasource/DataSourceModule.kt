package com.fourcutbook.forcutbook.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    private const val USER_PREFERENCE = "USER_PREFERENCE"

    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCE)

    @Provides
    @Singleton
    fun providesUserDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.userDataStore
}
