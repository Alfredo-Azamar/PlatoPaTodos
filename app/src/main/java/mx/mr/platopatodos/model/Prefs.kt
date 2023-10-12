package mx.mr.platopatodos.model

import android.content.Context

class Prefs(val context: Context) {

    // Shared Preferences DB Name
    val SHARED_NAME = "MyPrefs"

    // Location Key
    val SHARED_LOCATION = "Location"

    // Prefs DB mode
    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveLocation(name: String){
        storage.edit().putString(SHARED_LOCATION, name).apply()
    }

    fun getLocation(): String {
        return storage.getString(SHARED_LOCATION, "")!!
    }
}