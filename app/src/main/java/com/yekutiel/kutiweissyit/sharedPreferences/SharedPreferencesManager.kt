package com.yekutiel.kutiweissyit.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

const val SEARCH_TEXT = "SEARCH_TEXT"

class SharedPreferencesManager(context: Context?) {
    private val preferences: SharedPreferences? =
        context?.getSharedPreferences("com.yekutiel.kutiweissyit.sharedPreferences", Context.MODE_PRIVATE)

    fun saveLastSearchText(searchText: String?) {
        val editor = preferences?.edit()
        editor?.putString(SEARCH_TEXT, searchText)
        editor?.apply()
    }

    fun getLastSearchText(): String? {
        return preferences?.getString(SEARCH_TEXT, "")
    }
}