package com.pritamworld.androidweatherapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {

        private val LAST_CITY =
            stringPreferencesKey("last_searched_city")
    }

    suspend fun saveLastCity(
        city: String
    ) {

        dataStore.edit { preferences ->

            preferences[LAST_CITY] = city
        }
    }

    suspend fun getLastCity(): String? {

        return dataStore.data
            .first()[LAST_CITY]
    }
}