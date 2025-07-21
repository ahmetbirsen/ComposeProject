package com.example.composeproject.core.network

import kotlinx.serialization.json.JsonElement

interface INetworkError {
    var message: String?
    var data: JsonElement?
    var code: Int?
}
