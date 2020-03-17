package com.sandrocaseiro.template.handlers

import com.sandrocaseiro.template.exceptions.BaseException
import com.sandrocaseiro.template.exceptions.BindValidationException
import com.sandrocaseiro.template.exceptions.CustomErrors
import com.sandrocaseiro.template.exceptions.PageableBadRequestException
import com.sandrocaseiro.template.models.DResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(private val messageSource: MessageSource) : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    fun handleGeneral(e: Exception, request: WebRequest): ResponseEntity<Any> {
        return if (BaseException::class.java.isAssignableFrom(e.javaClass))
            handleException((e as BaseException).error, e.message, e)
        else
            handleException(CustomErrors.SERVER_ERROR, e.message, e)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(e: AccessDeniedException, request: WebRequest): ResponseEntity<Any> =
        handleException(CustomErrors.FORBIDDEN_ERROR, e.message, e)

    @ExceptionHandler(BindValidationException::class)
    fun handleBindingValidation(e: BindValidationException, request: WebRequest): ResponseEntity<Any> {
        val errors = mutableListOf<DResponse.Error>()
        for (error: ObjectError in e.bindErrors.allErrors) {
            if (FieldError::class.java.isAssignableFrom(error.javaClass)) {
                val fieldError = error as FieldError
                if (fieldError.field.isEmpty())
                    continue

                val message: String = fieldError.defaultMessage?.replace("{field}", fieldError.field) ?: ""
                errors.add(DResponse.Error.error(CustomErrors.BINDING_VALIDATION_ERROR.code, message))
            } else
                errors.add(DResponse.Error.error(CustomErrors.BINDING_VALIDATION_ERROR.code, error.defaultMessage ?: ""))
        }

        return handleException(CustomErrors.BINDING_VALIDATION_ERROR, errors, e)
    }

    @ExceptionHandler(PageableBadRequestException::class)
    fun handlePageableBadRequestException(e: PageableBadRequestException, request: WebRequest): ResponseEntity<Any> =
        handleException(e.error, e.fieldName, e)

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException::class)
    fun handleExpiredToken(e: io.jsonwebtoken.ExpiredJwtException, request: WebRequest): ResponseEntity<Any> =
        handleException(CustomErrors.TOKEN_EXPIRED_ERROR, e.message, e)

    @ExceptionHandler(io.jsonwebtoken.SignatureException::class)
    fun handleInvalidToken (e: io.jsonwebtoken.SignatureException, request: WebRequest): ResponseEntity<Any> =
        handleException(CustomErrors.INVALID_TOKEN_ERROR, e.message, e)

    override fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException, headers: HttpHeaders,
                                              status: HttpStatus, request: WebRequest): ResponseEntity<Any> =
        handleException(CustomErrors.BAD_REQUEST_ERROR, e.message, e)

    override fun handleHttpMediaTypeNotSupported(e: HttpMediaTypeNotSupportedException, headers: HttpHeaders,
                                                 status: HttpStatus, request: WebRequest): ResponseEntity<Any> =
        handleException(CustomErrors.UNSUPPORTED_MEDIA_TYPE, e.message, e)

    override fun handleHttpRequestMethodNotSupported(e: HttpRequestMethodNotSupportedException, headers: HttpHeaders,
                                                     status: HttpStatus, request: WebRequest): ResponseEntity<Any> =
        handleException(CustomErrors.METHOD_NOT_ALLOWED_ERROR, e.message, e)

    private fun handleException(error: CustomErrors, message: String?, e: Exception): ResponseEntity<Any> =
        handleException(error, arrayOf(message ?: ""), e)

    private fun handleException(error: CustomErrors, params: Array<*>, e: Exception): ResponseEntity<Any> {
        val message: String = messageSource.getMessage(error.messageRes, params, LocaleContextHolder.getLocale())
        logger.error(message, e)

        return ResponseEntity(DResponse.notOk(error.code, message), error.httpStatus)
    }

    private fun handleException(error: CustomErrors, errors: List<DResponse.Error>, e: Exception): ResponseEntity<Any> {
        logger.error("Error", e)

        return ResponseEntity(DResponse.notOk(errors), error.httpStatus)
    }
}
