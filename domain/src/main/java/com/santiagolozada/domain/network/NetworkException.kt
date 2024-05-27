package com.santiagolozada.domain.network

sealed class NetworkException(message: String) : Exception(message) {
    class BadRequestException(message: String) : NetworkException(message)
    class UnauthorizedException(message: String) : NetworkException(message)
    class ForbiddenException(message: String) : NetworkException(message)
    class NotFoundException(message: String) : NetworkException(message)
    class InternalServerErrorException(message: String) : NetworkException(message)
    class UnknownNetworkException(message: String) : NetworkException(message)
}