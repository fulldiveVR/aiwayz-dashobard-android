package com.wize.dashboard.model

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {

    val dataStore = context.dataStore
    private val onboardingShownPref = booleanPreferencesKey("onboarding_shown")

    val onboardingShown = dataStore.data
        .map { preferences ->
            preferences[onboardingShownPref] != null && preferences[onboardingShownPref] == true
        }

    suspend fun setOnboardingShown() {
        dataStore.edit { settings ->
            settings[onboardingShownPref] = true
        }
    }
}