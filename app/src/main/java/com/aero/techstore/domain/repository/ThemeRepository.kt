package com.aero.techstore.domain.repository

import com.aero.techstore.ui.theme.AppThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val themeMode: Flow<AppThemeMode>
    suspend fun setThemeMode(mode: AppThemeMode)
}
