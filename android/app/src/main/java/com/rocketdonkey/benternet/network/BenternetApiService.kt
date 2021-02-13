package com.rocketdonkey.benternet.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Benternet API interface.
 */
interface BenternetApiService {
    @POST("command")
    fun sendCommands(@Body commands: CommandList): Call<ResponseBody>
}

/**
 * Benternet API.
 *
 * This is a pretty straightforward API - make some `Command`s and send.
 *
 * See `BenternetCommand` for pre-packaged commands.
 */
object BenternetApi {
    private val TAG: String? = BenternetApi::class.simpleName

    /** Server URL. */
    private const val BASE_URL = "http://remotes.local"

    /** Moshi converter for server-bound JSON. */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /** Retrofit instance for communicating with the server. */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    /** Retrofit service used to communicate with the server. */
    private val retrofitService: BenternetApiService by lazy {
        retrofit.create(BenternetApiService::class.java)
    }

    /**
     * Sends `commands` to the server.
     *
     * Successful responses are returned by way of sparking joy via a great command execution.
     *
     * Failures are logged, making them useful to essentially nobody.
     */
    fun sendCommands(commands: CommandList) {
        Log.i(TAG, "Sending commands: $commands")

        retrofitService.sendCommands(commands).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d(TAG, "Successfully executed commands")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failure executing commands: ${t.message}")
                }
            })
    }

    /**
     * Overload for sending a single command to the server.
     *
     * The server always expects a list of commands, but callers should be shackled with that
     * restriction.
     */
    fun sendCommands(command: Command) {
        return sendCommands(CommandList(listOf(command)))
    }
}
