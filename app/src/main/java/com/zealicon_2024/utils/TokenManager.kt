package com.zealicon_2024.utils

import android.content.Context
import com.zealicon_2024.utils.Constants.PHONE_NUMBER
import com.zealicon_2024.utils.Constants.ID_CARD
import com.zealicon_2024.utils.Constants.NAME
import com.zealicon_2024.utils.Constants.PREFS_TOKEN_FILE
import com.zealicon_2024.utils.Constants.USER_ID
import com.zealicon_2024.utils.Constants.USER_TOKEN
import com.zealicon_2024.utils.Constants.ZEAL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager@Inject constructor(@ApplicationContext context: Context ) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String?){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
    fun savePhoneNumber(number:String){
        val editor=prefs.edit()
        editor.putString(PHONE_NUMBER,number)
        editor.apply()
    }

    fun clearSharedPreferences() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun getPhoneNumber(): String? {
        return prefs.getString(PHONE_NUMBER,null)
    }

    fun saveZeal(zealID: String?) {
        val editor = prefs.edit()
        editor.putString(ZEAL_ID, zealID)
        editor.apply()
    }
    fun saveName(name: String?) {
        val editor = prefs.edit()
        editor.putString(NAME, name)
        editor.apply()
    }

    fun saveUserId(userId: String?) {
        val editor = prefs.edit()
        editor.putString(USER_ID, userId)
        editor.apply()
    }

    fun getUserId(): String?{
        return prefs.getString(USER_ID, null)
    }


    fun saveID(id: String?) {
        val editor = prefs.edit()
        editor.putString(ID_CARD, id)
        editor.apply()
    }

    fun getZeal(): String? {
        return prefs.getString(ZEAL_ID, null)
    }

    fun getName(): String? {
        return prefs.getString(NAME, null)
    }

    fun getID(): String? {
        return prefs.getString(ID_CARD, null)
    }


}