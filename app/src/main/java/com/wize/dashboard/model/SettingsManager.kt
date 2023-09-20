package com.wize.dashboard.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(context: Context) {

    private val dataStore = context.dataStore
    private val startCounterPref = intPreferencesKey("start_counter")
    private val onboardingShownPref = booleanPreferencesKey("onboarding_shown")
    private val rateUsDonePref = booleanPreferencesKey("rate_us_done")

    val startCounter: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[startCounterPref] ?: 0
        }

    val onboardingShown = dataStore.data
        .map { preferences ->
            preferences[onboardingShownPref] ?: false
        }

    val rateUsDone = dataStore.data
        .map { preferences ->
            preferences[rateUsDonePref] ?: false
        }

    suspend fun setStartAppCounter(value: Int) {
        dataStore.edit { settings ->
            settings[startCounterPref] = value
        }
    }

    suspend fun setOnboardingShown() {
        dataStore.edit { settings ->
            settings[onboardingShownPref] = true
        }
    }

    suspend fun setRateUsDone() {
        dataStore.edit { settings ->
            settings[rateUsDonePref] = true
        }
    }
}