package com.innov8eg.impactyn.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlin.reflect.KClass

object JsonConverter {
    fun <T> convertJsonToObj(json: String?, classType: Class<T>): T? {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<T> = moshi.adapter(classType)
        json?.let {
            return adapter.fromJson(json)
        }
        return null
    }
}