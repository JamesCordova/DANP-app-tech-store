package com.aero.techstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.repository.ThemeRepository
import com.aero.techstore.ui.theme.AppThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {

    val themeMode: StateFlow<AppThemeMode> = themeRepository.themeMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppThemeMode.BLUE
        )

    fun setThemeMode(mode: AppThemeMode) {
        viewModelScope.launch {
            themeRepository.setThemeMode(mode)
        }
    }
    
    fun onThemeChange(modeName: String) {
        val mode = try {
            AppThemeMode.valueOf(modeName)
        } catch (_: Exception) {
            AppThemeMode.BLUE
        }
        setThemeMode(mode)
    }
}
