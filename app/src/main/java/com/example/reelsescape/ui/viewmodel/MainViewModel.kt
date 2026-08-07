package com.example.reelsescape.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.reelsescape.database.EscapeEventEntity
import com.example.reelsescape.model.AppSettings
import com.example.reelsescape.repository.EscapeRepository
import com.example.reelsescape.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository = SettingsRepository(application)
    private val escapeRepository = EscapeRepository(application)

    private val _settingsState = MutableStateFlow(settingsRepository.settings)
    val settingsState: StateFlow<AppSettings> = _settingsState.asStateFlow()

    val todayEscapes: LiveData<Int> = escapeRepository.todayCount
    val weeklyEscapes: LiveData<Int> = escapeRepository.weeklyCount
    val monthlyEscapes: LiveData<Int> = escapeRepository.monthlyCount
    val totalEscapes: LiveData<Int> = escapeRepository.totalCount

    val youtubeEscapesToday: LiveData<Int> = escapeRepository.getTodayCountByPackage(com.example.reelsescape.constants.AppConstants.PACKAGE_YOUTUBE)
    val instagramEscapesToday: LiveData<Int> = escapeRepository.getTodayCountByPackage(com.example.reelsescape.constants.AppConstants.PACKAGE_INSTAGRAM)
    val facebookEscapesToday: LiveData<Int> = escapeRepository.getTodayCountByPackage(com.example.reelsescape.constants.AppConstants.PACKAGE_FACEBOOK)

    val youtubeTotalEscapes: LiveData<Int> = escapeRepository.getCountByPackage(com.example.reelsescape.constants.AppConstants.PACKAGE_YOUTUBE)
    val instagramTotalEscapes: LiveData<Int> = escapeRepository.getCountByPackage(com.example.reelsescape.constants.AppConstants.PACKAGE_INSTAGRAM)
    val facebookTotalEscapes: LiveData<Int> = escapeRepository.getCountByPackage(com.example.reelsescape.constants.AppConstants.PACKAGE_FACEBOOK)

    val recentEvents: LiveData<List<EscapeEventEntity>> = escapeRepository.getRecentEvents(10)

    fun refreshSettings() {
        _settingsState.value = settingsRepository.settings
    }

    fun setMasterProtection(enabled: Boolean) {
        settingsRepository.setMasterProtection(enabled)
        refreshSettings()
    }

    fun setYoutubeEnabled(enabled: Boolean) {
        settingsRepository.setYoutubeEnabled(enabled)
        refreshSettings()
    }

    fun setInstagramEnabled(enabled: Boolean) {
        settingsRepository.setInstagramEnabled(enabled)
        refreshSettings()
    }

    fun setFacebookEnabled(enabled: Boolean) {
        settingsRepository.setFacebookEnabled(enabled)
        refreshSettings()
    }

    fun setSensitivity(sensitivity: String) {
        settingsRepository.setSensitivity(sensitivity)
        refreshSettings()
    }

    fun setDebugMode(enabled: Boolean) {
        settingsRepository.setDebugMode(enabled)
        refreshSettings()
    }

    fun setOnboardingCompleted(completed: Boolean) {
        settingsRepository.setOnboardingCompleted(completed)
        refreshSettings()
    }

    fun clearHistory() {
        escapeRepository.clearHistory()
    }

    fun resetSettings() {
        settingsRepository.resetSettings()
        refreshSettings()
    }
}
