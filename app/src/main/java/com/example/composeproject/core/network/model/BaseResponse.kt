package com.example.composeproject.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    @SerialName("status")
    var status: ResponseStatus? = null
)
