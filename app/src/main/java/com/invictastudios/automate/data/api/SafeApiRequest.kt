package com.invictastudios.automate.data.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.invictastudios.automate.data.model.ErrorResponse
import org.json.JSONException
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.UndeclaredThrowableException
import java.net.ConnectException
import java.net.SocketTimeoutException

private const val TAG = "SafeApiRequest"

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {
        val message = StringBuilder()
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                Log.d(TAG, "Response successful: $response")
                return response.body()
            } else {
                Log.d(TAG, "Response NOT successful: $response")
                if (response.code() != 200) {

                    try {

                        val error = response.errorBody()?.string()

                        val responseerror: ErrorResponse? =
                            Gson().fromJson(error, ErrorResponse::class.java)

                        when {
                            response.code() == 401 -> {

                                exception(
                                    responseerror?.message ?: "",
                                    true
                                )
                            }

                            response.code() == 400 -> {
                                exception(
                                    responseerror?.message ?: "",
                                    false
                                )
                            }

                            response.code() == 500 -> {
                                exception(
                                    responseerror?.message ?: "",
                                    false
                                )
                            }

                            else -> exception(responseerror?.message ?: "", false)
                        }
                    } catch (e: JSONException) {
                        exception("", false)
                    } catch (e: JsonParseException) {
                        exception("", false)
                    } catch (e: JsonParseException) {
                        exception("", false)
                    } catch (e: IllegalStateException) {
                        exception("", false)
                    } catch (e: JsonSyntaxException) {
                        exception("", false)
                    } catch (e: ConnectException) {
                        exception("", false)
                    }
                }
            }
        } catch (e: NoInternetException) {
            e.message?.let { exception(it, false) }
        } catch (e: UndeclaredThrowableException) {
            message.append("")
            exception(message.toString(), false)
        } catch (e: SocketTimeoutException) {
            message.append("")
            exception(message.toString(), false)
        } catch (e: IOException) {
            message.append("")
            exception(message.toString(), false)
        }
        return null
    }

    abstract fun exception(errorMsg: String, isUnauthorized: Boolean)
}
