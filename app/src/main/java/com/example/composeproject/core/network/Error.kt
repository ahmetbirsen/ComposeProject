package com.example.composeproject.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
class Error : INetworkError {
    @SerialName("message")
    override var message: String? = null

    @SerialName("code")
    override var code: Int? = null

    @SerialName("data")
    override var data: JsonElement? = null

    @SerialName("externalCode")
    override var externalCode: String? = null

    companion object {

        fun from(errorType: NetworkErrorType): Error {
            val error = Error()
            error.code = errorType.value
            return error
        }

        fun from(message: String?): Error {
            val error = Error()
            error.code = NetworkErrorType.OTHER.value
            error.message = message
            return error
        }
    }
}
