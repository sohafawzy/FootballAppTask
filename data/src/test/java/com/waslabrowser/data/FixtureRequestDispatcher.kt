package com.waslabrowser.data

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection


class FixtureRequestDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/competitions/2021/matches" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/matches.json"))
            }
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path.toString()}")
        }
    }
}