package com.waslabrowser.data.remote

import com.waslabrowser.data.remote.models.MatchesResponse
import com.waslabrowser.domain.interactors.GetMatchUseCase
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{path}")
    suspend fun getApi(@Path(value = "path",encoded = true) path: String): Response<ResponseBody>

}