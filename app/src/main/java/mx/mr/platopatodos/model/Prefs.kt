package mx.mr.platopatodos.model

import android.content.Context

/**
 * Preferences
 *
 * This class provides a convenient way to manage and access shared preferences in the application.
 * It includes methods for saving and retrieving various settings.
 *
 * @param context The application context.
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
    // Menu uploaded
    val SHARED_MENU = "MenuUploaded"

    // Prefs DB mode
    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    /**
     * Saves the current dining location in shared preferences.
     *
     * @param name The name of the dining location to be saved.
     */
    fun saveLocation(name: String){
        storage.edit().putString(SHARED_LOCATION, name).apply()
    }

    /**
     * Saves the current status for clickable items in shared preferences.
     *
     * @param status The status to be saved (true or false).
     */
    fun saveStautsCV(status: Boolean){
        storage.edit().putBoolean(SHARED_STATUS_CV, status).apply()
    }

    /**
     * Saves the login status in shared preferences.
     *
     * @param loginStatus The login status to be saved (true or false).
     */
    fun saveLogin(loginStatus: Boolean) {
        storage.edit().putBoolean(SHARED_LOG_KEY, loginStatus).apply()
    }

    /**
     * Saves the menu upload status in shared preferences.
     *
     * @param menuStatus The menu upload status to be saved (true or false).
     */
    fun saveUpMenu(menuStatus: Boolean) {
        storage.edit().putBoolean(SHARED_MENU, menuStatus).apply()
    }

    /**
     * Retrieves the current dining location from shared preferences.
     *
     * @return The current dining location.
     */
    fun getLocation(): String {
        return storage.getString(SHARED_LOCATION, "")!!
    }

    /**
     * Retrieves the current status for clickable items from shared preferences.
     *
     * @return The current status (true or false).
     */
    fun getStatus(): Boolean {
        return storage.getBoolean(SHARED_STATUS_CV,false)
    }

    /**
     * Retrieves the login status from shared preferences.
     *
     * @return The login status (true or false).
     */
    fun getLogin(): Boolean {
        return storage.getBoolean(SHARED_LOG_KEY, false)
    }

    /**
     * Retrieves the menu upload status from shared preferences.
     *
     * @return The menu upload status (true or false).
     */
    fun getUpMenu(): Boolean {
        return storage.getBoolean(SHARED_MENU, false)
    }

    /**
     * Deletes all current preferences in shared preferences.
     */
    fun wipe() {
        storage.edit().clear().apply()
    }
}