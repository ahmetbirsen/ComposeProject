package com.example.composeproject.core.network

enum class NetworkErrorType(val value: Int) {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    TOO_MANY_REQUESTS(429),
    REQUEST_FAILED(-1),
    OTHER(-2),
    NO_INTERNET_CONNECTION(-1000),
    NO_CONNECTION(-1001),
}

fun NetworkErrorType.toError(): Error {
    return Error.from(this)
}
