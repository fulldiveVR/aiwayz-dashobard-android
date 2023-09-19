package com.wize.dashboard.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wize.dashboard.extensions.openAppInGooglePlay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PopupsViewModel(private val settingsManager: SettingsManager) : ViewModel() {

    val currentPopupFlow: MutableStateFlow<StartAppPopup> = MutableStateFlow(StartAppPopup.Empty)

    val rateUsDoneFlow: StateFlow<Boolean> = settingsManager.rateUsDone
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val rateUsReportFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val startCounterFlow: StateFlow<Int> = settingsManager.startCounter
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)


    private val popupsFlow = listOf(
        StartAppPopup.Empty,
        StartAppPopup.Empty,
        StartAppPopup.Empty,

        StartAppPopup.Empty,
        StartAppPopup.Empty,
        StartAppPopup.Empty,

        StartAppPopup.Empty,
        StartAppPopup.Empty,
        StartAppPopup.RateUs,

        StartAppPopup.Empty,
        StartAppPopup.Empty,
        StartAppPopup.Empty,
        StartAppPopup.RateUs,
    )

    private val client = OkHttpClient()

    init {
        currentPopupFlow.value = getShowingPopup(startCounterFlow.value)
        viewModelScope.launch {
            settingsManager.setStartAppCounter(startCounterFlow.value + 1)
        }
    }

    private fun getShowingPopup(startCounter: Int): StartAppPopup {
        return if (popupsFlow.lastIndex >= startCounter) {
            popupsFlow[startCounter]
        } else {
            popupsFlow[startCounter % popupsFlow.size]
        }
    }

    fun onRateUsPositiveClicked(
        context: Context,
        rating: Int
    ) {
        if (rating < SUCCESS_RATING_VALUE) {
            rateUsReportFlow.value = true
        } else {
            context.openAppInGooglePlay(appPackageName = context.applicationInfo.packageName)

            viewModelScope.launch {
                settingsManager.setRateUsDone()
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = post(INBOX_URL, getJSON(message))
                Log.d("sendMessageTest", res)
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                Log.d("sendMessageTest", "$ex.message")
            }
            settingsManager.setRateUsDone()
        }
    }

    private fun getJSON(message: String): String {
        return "{\"payload\":{\"message\":\"$message\"},\"type\":\"report-message\"}"
    }

    @Throws(IOException::class)
    private fun post(url: String, json: String): String {
        val body: RequestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        var result = ""
        client.newCall(request).execute().use { response ->
            result = response.body.toString()
        }
        return result
    }

    class Factory(
        private val settingsManager: SettingsManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PopupsViewModel(settingsManager) as T
        }
    }

    companion object {
        private const val SUCCESS_RATING_VALUE = 4
        private const val INBOX_URL = "https://api.fdvr.co/v2/inbox"
    }
}

sealed class StartAppPopup(val id: String) {
    object RateUs : StartAppPopup("RateUs")
    object InstallBrowser : StartAppPopup("InstallBrowser")
    object Empty : StartAppPopup("Empty")
}