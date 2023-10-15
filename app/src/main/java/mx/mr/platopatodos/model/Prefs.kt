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
    // COSOPREF
    val SHARED_STATUS_CV = "Status"

    // Prefs DB mode
    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveLocation(name: String){
        storage.edit().putString(SHARED_LOCATION, name).apply()
    }
    //COSO SET
    fun saveStautsCV(status: Boolean){
        storage.edit().putBoolean(SHARED_STATUS_CV, status).apply()
    }

    fun getLocation(): String {
        return storage.getString(SHARED_LOCATION, "")!!
    }
    //COSO GET
    fun getStatus(): Boolean {
        return storage.getBoolean(SHARED_STATUS_CV,false)
    }
}