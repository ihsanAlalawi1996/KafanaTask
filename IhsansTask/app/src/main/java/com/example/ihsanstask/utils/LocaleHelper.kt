package com.example.ihsanstask.utils

import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

/**
 * this class to is responsible for handling the language things
 *
 */

class LocaleHelper @Inject constructor(@ApplicationContext val context: Context) {
    val isEnglish: Boolean
        get() = Locale.getDefault().language == "en"

    companion object {
        private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    }

    fun onAttach(context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang!!)
    }

    fun onAttach(context: Context, defaultLanguage: String): Context =
        setLocale(context, getPersistedData(context, defaultLanguage)!!)

    val getLanguage: String
        get() = getPersistedData(context, Locale.getDefault().language) ?: "en"

    private fun setLocale(context: Context, language: String): Context {
        persist(context, language)
        return updateResources(context, language)
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? =
        PreferenceManager.getDefaultSharedPreferences(context)
            .getString(SELECTED_LANGUAGE, defaultLanguage)

    private fun persist(context: Context, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration =
            context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}