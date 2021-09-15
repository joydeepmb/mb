package app.jyoti.util

import android.content.Context
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.map


class PreferenceManager(context: Context) {
    // Create the dataStore and give it a name same as shared preferences
    private val dataStore = context.createDataStore(name = "user_prefs")

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val EMPLOYEE_CODE_KEY = preferencesKey<String>("EMPLOYEE_CODE")
        val EMPLOYEE_STATUS_KEY = preferencesKey<String>("EMPLOYEE_STATUS")
    }

    // Store user data
    // refer to the data store and using edit
    // we can store values using the keys
    suspend fun storeUser(employeeCode: String, employeeStatus: String) {
        dataStore.edit {
            it[EMPLOYEE_CODE_KEY] = employeeCode
            it[EMPLOYEE_STATUS_KEY] = employeeStatus

            // here it refers to the preferences we are editing

        }
    }

    // Create an age flow to retrieve age from the preferences
    // flow comes from the kotlin coroutine
    val employeeCodeFlow: Flow<String> = dataStore.data.map {
        it[EMPLOYEE_CODE_KEY] ?: ""
    }

    // Create a name flow to retrieve name from the preferences
    val employeeStatusFlow: Flow<String> = dataStore.data.map {
        it[EMPLOYEE_STATUS_KEY] ?: ""
    }

}