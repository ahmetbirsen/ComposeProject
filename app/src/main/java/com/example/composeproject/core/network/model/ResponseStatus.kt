package com.example.composeproject.core.network.model

import com.example.composeproject.core.network.IResponseStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseStatus(
    @SerialName("code")
    override val code: Int?,
    @SerialName("message")
    override val message: String?
) : IResponseStatus
