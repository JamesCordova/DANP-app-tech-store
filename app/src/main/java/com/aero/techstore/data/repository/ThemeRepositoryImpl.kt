package com.aero.techstore.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aero.techstore.domain.repository.ThemeRepository
import com.aero.techstore.ui.theme.AppThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : ThemeRepository {

    private object PreferencesKeys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    override val themeMode: Flow<AppThemeMode> = context.dataStore.data
        .map { preferences ->
            val modeName = preferences[PreferencesKeys.THEME_MODE] ?: AppThemeMode.BLUE.name
            try {
                AppThemeMode.valueOf(modeName)
            } catch (_: Exception) {
                AppThemeMode.BLUE
            }
        }

    override suspend fun setThemeMode(mode: AppThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = mode.name
        }
    }
}
