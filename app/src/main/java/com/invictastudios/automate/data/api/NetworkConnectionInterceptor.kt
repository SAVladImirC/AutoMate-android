package com.invictastudios.automate.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.text.format.Formatter
import okhttp3.Interceptor
import okhttp3.Response
import java.net.Inet4Address
import java.net.NetworkInterface

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable()) {
            throw NoInternetException("")
        }
        return chain.proceed(chain.request())
    }

    @Suppress("DEPRECATION")
    fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {

                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    else -> false
                }
            }
        }
        return result
    }

    fun getIpAddress(): String {
        var ip = ""
        try {
            val wm =
                appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            ip = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        } catch (e: java.lang.Exception) {
        }

        if (ip.isEmpty()) {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val networkInterface = en.nextElement()
                    val enumIpAddr = networkInterface.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                            val host = inetAddress.getHostAddress()
                            if (host.isNotEmpty()) {
                                ip = host
                                break
                            }
                        }
                    }
                }
            } catch (e: java.lang.Exception) {
            }
        }

        if (ip.isEmpty())
            ip = "0.0.0"
        return ip
    }
}
