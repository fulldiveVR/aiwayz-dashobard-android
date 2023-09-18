package com.wize.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wize.dashboard.model.SettingsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class OnboardingViewModel(private val settingsManager: SettingsManager) : ViewModel() {

    val onboardingShownFlow: StateFlow<Boolean> = settingsManager.onboardingShown
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    suspend fun setOnboardingShown() {
        settingsManager.setOnboardingShown()
    }

    class Factory(
        private val settingsManager: SettingsManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OnboardingViewModel(settingsManager) as T
        }
    }
}

