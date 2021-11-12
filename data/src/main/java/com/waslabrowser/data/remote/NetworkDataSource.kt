package com.waslabrowser.data.remote

import com.innov8eg.impactyn.utils.JsonConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class NetworkDataSource constructor(val apiService: ApiService) {

    suspend inline fun <reified T> makeGetRequest(path: String) : Flow<T?> = flow {
        val response = apiService.getApi(path)
        emit(JsonConverter.convertJsonToObj(response.body()?.string(),T::class.java))

    }
}