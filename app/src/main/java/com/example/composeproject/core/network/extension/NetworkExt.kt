package com.example.composeproject.core.network.extension

import com.example.composeproject.core.model.RestResult
import com.example.composeproject.core.network.Error
import com.example.composeproject.core.network.NetworkErrorType
import com.example.composeproject.core.network.di.JsonModule
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Response

val json = JsonModule.provideJson()

fun <T> parseError(response: Response<T>): RestResult.Error {

    val error = when (response.code()) {
        NetworkErrorType.UNAUTHORIZED.value -> Error.from(NetworkErrorType.UNAUTHORIZED)
        NetworkErrorType.FORBIDDEN.value -> Error.from(NetworkErrorType.FORBIDDEN)
        NetworkErrorType.TOO_MANY_REQUESTS.value -> Error.from(NetworkErrorType.TOO_MANY_REQUESTS)
        NetworkErrorType.NOT_FOUND.value -> Error.from(NetworkErrorType.OTHER)
        NetworkErrorType.BAD_REQUEST.value -> response.parseErrorBody()
        else -> {
            runCatching {
                val request = response.raw().request
                val path = request.url.toString()
                val requestJson = if (request.method.equals("post", ignoreCase = true) &&
                    request.body !is MultipartBody
                ) {
                    json.decodeFromString(request.body?.bodyToString().orEmpty())
                } else {
                    ""
                }
            }

            Error.from(NetworkErrorType.REQUEST_FAILED)
        }
    }
    return RestResult.Error(error)
}

inline val <reified T > Response<T>.asRestResult: RestResult<T>
    get() {
        try {
            if (isSuccessful.not()) {
                return parseError(this)
            } else {
                val body = body() ?: return createErrorResult(this)
                return RestResult.Success(body)
            }
        } catch (e: Exception) {
            return RestResult.Error(
                Error().apply {
                    message = e.message
                    code = NetworkErrorType.REQUEST_FAILED.value
                }
            )
        }
    }

fun RequestBody?.bodyToString(): String {
    if (this == null) return ""

    return try {
        val buffer = Buffer()
        writeTo(buffer)
        buffer.readUtf8()
    } catch (e: Exception) {
        ""
    }
}


fun <T > createErrorResult(response: Response<T>): RestResult.Error {
    return RestResult.Error(
        Error.from(NetworkErrorType.OTHER).apply {
            message = response.message()
        }
    )
}

fun Response<*>.parseErrorBody(): Error {
    val errorBody = errorBody()?.string()
    return Error().apply {
        code = code()
        message = try {
            val jsonObject = json.parseToJsonElement(errorBody ?: "{}").jsonObject
            jsonObject["status"]?.jsonObject?.get("message")?.jsonPrimitive?.content
        } catch (e: Exception) {
            message()
        }
    }
}