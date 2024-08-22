package com.fourcutbook.forcutbook.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultTokenRepository @Inject constructor(
    private val tokenDataStore: DataStore<Preferences>
) : TokenRepository {

    override val accessToken: Flow<String?> = tokenDataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN]
    }

    override val refreshToken: Flow<String?> = tokenDataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN]
    }

    override suspend fun updateAccessToken(accessToken: String) {
        tokenDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        tokenDataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun removeAll() {
        tokenDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override fun fetchUserId(): Flow<Long?> = tokenDataStore.data.map { preferences ->
        4
    }

    override suspend fun postUserId(id: Long) {
        tokenDataStore.edit { preferences ->
            preferences[USER_ID] = id
        }
    }

    override suspend fun postDelete() {
        tokenDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {

        private const val NO_ACCESSIBLE_USER = "There's no accessible user."
        private val USER_ID = longPreferencesKey("USER_ID")
        private val ACCESS_TOKEN: Preferences.Key<String> = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN: Preferences.Key<String> = stringPreferencesKey("REFRESH_TOKEN")
    }
}
