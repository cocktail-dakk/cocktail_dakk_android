package com.umcapplunching.cocktail_dakk.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreSearchStr(val context : Context){
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "dataStore")
    private val stringKey = stringPreferencesKey("searchStr") // string 저장 키값

    // Flow : coroutines.flow import 해야됨
    // Key-Value 쌍들의 집합을 Map형태로 반환하는데
    // 여기서 우리가 필요한 StringKey값으로 값을 뺴내야함
    suspend fun getSearchStr() : Flow<String> = context.dataStore.data
        // catch문으로 예외처리
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {preferences ->
            // stringKey 값을 넣어 key-value형태로 데이터 가져오기
            preferences[stringKey] ?: ""
        }

    // String값을 stringKey 키 값에 저장
    suspend fun setText(updateDay : String){
        context.dataStore.edit { preferences ->
            preferences[stringKey] = updateDay
        }
    }

//    fun getBaseUrlPreferencesFlow(): Flow<String> = context.dataStore.data
//        .catch { exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            } else {
//                throw exception
//            }
//        }
//        .map { preferences ->
//            preferences[stringKey] ?: ""
//        }
}