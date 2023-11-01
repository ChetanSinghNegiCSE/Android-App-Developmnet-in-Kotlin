package com.example.restapi.util

import android.content.Context
import android.content.SharedPreferences
import com.example.restapi.Model.PostModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPref(context : Context) {

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    // Shared pref mode
    var PRIVATE_MODE = 0

    init {
        pref = context.getSharedPreferences("PREF_NAME", PRIVATE_MODE)
        editor = pref.edit()
    }

    fun saveUserList(postList: List<PostModel>) {
        val userJson = Gson().toJson(postList)
        editor.putString("postList", userJson)
        editor.apply()
    }

    fun getUserList(): List<PostModel> {
        val userJson = pref.getString("userList", null)
        return if (userJson != null) {
            val type = object : TypeToken<List<PostModel>>() {}.type
            Gson().fromJson(userJson, type)
        } else {
            emptyList()
        }
    }

}