package com.waslabrowser.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.waslabrowser.data.local.FixtureDao
import com.waslabrowser.data.local.FixtureDatabase
import com.waslabrowser.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

open class BaseTest {

    private lateinit var db: FixtureDatabase
    protected lateinit var fixtureDao: FixtureDao

    private lateinit var mockWebServer: MockWebServer
    lateinit var apiService: ApiService
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var loggingInterceptor: HttpLoggingInterceptor

    @Before
    open fun setup(){
        setupDB()
        setupNetwork()
    }

    fun setupDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FixtureDatabase::class.java).build()
        fixtureDao = db.fixtureDao()


    }

    fun setupNetwork() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = FixtureRequestDispatcher()
        mockWebServer.start()
        loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = buildOkhttpClient(loggingInterceptor)

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        runBlocking(Dispatchers.IO) {
            db.clearAllTables()
        }

        db.close()
    }


    @After
    open fun closeNetwork() {
        mockWebServer.shutdown()
    }

    private fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }
}
