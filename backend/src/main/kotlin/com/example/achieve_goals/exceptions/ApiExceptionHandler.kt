package com.example.achieve_goals.exceptions

import com.example.achieve_goals.exceptions.abstracts.ApiBadRequestException
import com.example.achieve_goals.exceptions.abstracts.ApiConflictException
import com.example.achieve_goals.exceptions.abstracts.ApiNotfoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.ZoneId
import java.time.ZonedDateTime

@ControllerAdvice
class ApiExceptionHandler {

    private fun createErrorResponse(exception: Exception, httpStatus: HttpStatus): ApiException {
        return ApiException(
            message = exception.message,
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            timestamp = ZonedDateTime.now(ZoneId.of("Z"))
        )
    }

    @ExceptionHandler(value = [(ApiNotfoundException::class)])
    fun handleApiNotfoundException(exception: ApiNotfoundException): ResponseEntity<Any> {
        val httpStatus = HttpStatus.NOT_FOUND
        val apiException = createErrorResponse(exception, httpStatus)
        return ResponseEntity(apiException, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(ApiBadRequestException::class)])
    fun handleApiBadRequestException(exception: ApiBadRequestException): ResponseEntity<Any> {
        val httpStatus = HttpStatus.BAD_REQUEST
        val apiException = createErrorResponse(exception, httpStatus)
        return ResponseEntity(apiException, httpStatus)
    }

    @ExceptionHandler(value = [(ApiConflictException::class)])
    fun handleApiConflictException(exception: ApiConflictException): ResponseEntity<Any> {
        val httpStatus = HttpStatus.CONFLICT
        val apiException = createErrorResponse(exception, httpStatus)
        return ResponseEntity(apiException, httpStatus)
    }
}