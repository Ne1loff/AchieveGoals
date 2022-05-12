package com.example.achieve_goals.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.ZoneId
import java.time.ZonedDateTime

@ControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(value = [(ApiNotfoundException::class)])
    fun handleApiNotfoundException(exception: ApiNotfoundException): ResponseEntity<Any> {
        val httpStatus = HttpStatus.NOT_FOUND
        val apiException = ApiException(
            message = exception.message,
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            timestamp = ZonedDateTime.now(ZoneId.of("Z"))
        )
        return ResponseEntity(apiException, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(ApiBadRequestException::class)])
    fun handleApiBadRequestException(exception: ApiBadRequestException): ResponseEntity<Any> {
        val httpStatus = HttpStatus.BAD_REQUEST
        val apiException = ApiException(
            message = exception.message,
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            timestamp = ZonedDateTime.now(ZoneId.of("Z"))
        )
        return ResponseEntity(apiException, httpStatus)
    }

    @ExceptionHandler(value = [(ApiInvalidLoginOrPasswordException::class)])
    fun handleApiIncorrectLoginOrPasswordException(exception: ApiInvalidLoginOrPasswordException): ResponseEntity<Any> {
        val httpStatus = HttpStatus.UNAUTHORIZED
        val apiException = ApiException(
            message = exception.message,
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            timestamp = ZonedDateTime.now(ZoneId.of("Z"))
        )
        return ResponseEntity(apiException, httpStatus)
    }
}