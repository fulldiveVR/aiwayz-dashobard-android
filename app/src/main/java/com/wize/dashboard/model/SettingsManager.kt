package com.wize.dashboard.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wize.dashboard.extensions.unsafeLazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(context: Context) {

    private val dataStore = context.dataStore
    private val startCounterPref by unsafeLazy { intPreferencesKey("start_counter") }
    private val onboardingShownPref by unsafeLazy { booleanPreferencesKey("onboarding_shown") }
    private val rateUsDonePref by unsafeLazy { booleanPreferencesKey("rate_us_done") }

    val startCounter: Flow<Int> = dataStore.data
        .mapNotNull { preferences ->
            preferences[startCounterPref]
        }

    val onboardingShown = dataStore.data
        .map { preferences ->
            preferences[onboardingShownPref] != null && preferences[onboardingShownPref] == true
        }

    val rateUsDone = dataStore.data
        .map { preferences ->
            preferences[rateUsDonePref] != null && preferences[rateUsDonePref] == true
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