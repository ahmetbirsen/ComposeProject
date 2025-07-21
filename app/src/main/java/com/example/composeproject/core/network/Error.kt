package com.example.composeproject.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import com.example.composeproject.core.network.NoInternetConnectionException

@Serializable
class Error : INetworkError {
    @SerialName("message")
    override var message: String? = null

    @SerialName("code")
    override var code: Int? = null

    @SerialName("data")
    override var data: JsonElement? = null

    companion object {

        fun from(errorType: NetworkErrorType): Error {
            val error = Error()
            error.code = errorType.value
            error.message = when (errorType) {
                NetworkErrorType.NO_INTERNET_CONNECTION -> "İnternet bağlantınızı kontrol edin"
                NetworkErrorType.NO_CONNECTION -> "Bağlantı hatası oluştu"
                NetworkErrorType.BAD_REQUEST -> "Geçersiz istek"
                NetworkErrorType.UNAUTHORIZED -> "Yetkisiz erişim"
                NetworkErrorType.FORBIDDEN -> "Erişim reddedildi"
                NetworkErrorType.NOT_FOUND -> "Bulunamadı"
                NetworkErrorType.TOO_MANY_REQUESTS -> "Çok fazla istek"
                NetworkErrorType.REQUEST_FAILED -> "İstek başarısız"
                NetworkErrorType.OTHER -> "Bir hata oluştu"
            }
            return error
        }

        fun from(message: String?): Error {
            val error = Error()
            error.code = NetworkErrorType.OTHER.value
            error.message = message
            return error
        }

        fun fromException(exception: Throwable): Error {
            return when (exception) {
                is NoInternetConnectionException -> from(NetworkErrorType.NO_INTERNET_CONNECTION)
                else -> when (exception.message) {
                    "NoInternetConnection" -> from(NetworkErrorType.NO_INTERNET_CONNECTION)
                    else -> from(exception.message ?: "Bilinmeyen hata")
                }
            }
        }
    }
}
