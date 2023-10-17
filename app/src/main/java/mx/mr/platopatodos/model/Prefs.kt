package mx.mr.platopatodos.model

import android.content.Context

/**
 * Preferences
 * @author Héctor González Sánchez
 */

class Prefs(val context: Context) {

    // Shared Preferences DB Name
    val SHARED_NAME = "MyPrefs"

    // Location Key
    val SHARED_LOCATION = "Location"
    // Dining status
    val SHARED_STATUS_CV = "Status"
    // Login status
    val SHARED_LOG_KEY = "Logkey"

    // Prefs DB mode
    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    // Save current dining location
    fun saveLocation(name: String){
        storage.edit().putString(SHARED_LOCATION, name).apply()
    }
    // Save current status for clickable items
    fun saveStautsCV(status: Boolean){
        storage.edit().putBoolean(SHARED_STATUS_CV, status).apply()
    }

    // Save log-in key
    fun saveLogin(loginStatus: Boolean) {
        storage.edit().putBoolean(SHARED_LOG_KEY, loginStatus).apply()
    }

    // Get current dining location
    fun getLocation(): String {
        return storage.getString(SHARED_LOCATION, "")!!
    }

    // Get current status for clickable items
    fun getStatus(): Boolean {
        return storage.getBoolean(SHARED_STATUS_CV,false)
    }

    // Get log-in key
    fun getLogin(): Boolean {
        return storage.getBoolean(SHARED_LOG_KEY, false)
    }

    // Delete all current preferences
    fun wipe() {
        storage.edit().clear().apply()
    }
}