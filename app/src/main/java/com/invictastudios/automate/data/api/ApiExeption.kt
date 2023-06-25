package com.invictastudios.automate.data.api

import java.io.IOException
import java.net.SocketTimeoutException

class NoInternetException(message: String) : IOException(message)
class Exception(message: String) : SocketTimeoutException(message)
