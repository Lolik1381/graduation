package ru.stankin.graduation.controller

import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.exception.ApplicationException

@ControllerAdvice(basePackages = ["ru.stankin.graduation.controller"])
class AdviceController {

    companion object {
        private val logger = LoggerFactory.getLogger(AdviceController::class.java)
    }

    @ExceptionHandler(RuntimeException::class)
    fun catchRuntimeExceptions(request: HttpServletRequest, exception: RuntimeException): ResponseEntity<CommonResponse<*>> {
        logger.warn(exception.message, exception)

        return when (exception) {
            is ApplicationException -> ResponseEntity(CommonResponse<Any>(exception.message.orEmpty()), HttpStatus.BAD_REQUEST)
            else -> ResponseEntity(CommonResponse<Any>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.message.orEmpty()), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @ExceptionHandler(
        HttpRequestMethodNotSupportedException::class,
        HttpMediaTypeNotSupportedException::class,
        HttpMediaTypeNotAcceptableException::class,
        MissingServletRequestParameterException::class,
        ServletRequestBindingException::class,
        TypeMismatchException::class,
        HttpMessageNotReadableException::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestPartException::class,
        BindException::class,
        NoHandlerFoundException::class,
    )
    fun handle400xExceptions(request: HttpServletRequest, exception: Exception): ResponseEntity<CommonResponse<*>> {
        logger.warn("Exception info: ${exception.message}", exception)

        return when (exception) {
            is NoHandlerFoundException -> ResponseEntity(CommonResponse<Any>(HttpStatus.NOT_FOUND.value(), exception.message.orEmpty()), HttpStatus.NOT_FOUND)
            else -> ResponseEntity(CommonResponse<Any>(HttpStatus.BAD_REQUEST.value(), exception.message.orEmpty()), HttpStatus.BAD_REQUEST)
        }
    }
}