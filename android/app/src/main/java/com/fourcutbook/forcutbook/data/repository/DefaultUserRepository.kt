package com.fourcutbook.forcutbook.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val userDataStore: DataStore<Preferences>
) : UserRepository {

    override fun fetchUserId(): Flow<Long> = userDataStore.data.map { preferences ->
        preferences[USER_ID] ?: throw NoSuchElementException(NO_ACCESSIBLE_USER)
    }

    override suspend fun postUserId(id: Long) {
        userDataStore.edit { preferences ->
            preferences[USER_ID] = id
            Log.d("woogi", "postUserId: $id")
        }
    }

    override suspend fun postDelete() {
        userDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {

        private const val NO_ACCESSIBLE_USER = "There's no accessible user."
        private val USER_ID = longPreferencesKey("USER_ID")
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
    }
}
