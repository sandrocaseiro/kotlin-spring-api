package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.BaseException
import dev.sandrocaseiro.template.exceptions.BindValidationException
import dev.sandrocaseiro.template.exceptions.PageableBadRequestException
import dev.sandrocaseiro.template.localization.IMessageSource
import dev.sandrocaseiro.template.models.DResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(private val messageSource: IMessageSource) : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    fun handleGeneral(e: Exception, request: WebRequest): ResponseEntity<Any> {
        return if (BaseException::class.java.isAssignableFrom(e.javaClass))
            handleException((e as BaseException).error, e.message, e)
        else
            handleException(AppErrors.SERVER_ERROR, e.message, e)
    }

    @ExceptionHandler(AccessDeniedException::class, AuthenticationException::class)
    fun handleAccessDenied(e: Exception, request: WebRequest): ResponseEntity<Any> =
        handleException(AppErrors.FORBIDDEN_ERROR, e.message, e)

    @ExceptionHandler(BindValidationException::class)
    fun handleBindingValidation(e: BindValidationException, request: WebRequest): ResponseEntity<Any> {
        val errors = mutableListOf<DResponse.Error>()
        for (error: ObjectError in e.bindErrors.allErrors) {
            if (FieldError::class.java.isAssignableFrom(error.javaClass)) {
                val fieldError = error as FieldError
                if (fieldError.field.isEmpty())
                    continue

                val message: String = fieldError.defaultMessage?.replace("{field}", fieldError.field) ?: ""
                errors.add(DResponse.Error.error(AppErrors.BINDING_VALIDATION_ERROR.code, message))
            } else
                errors.add(DResponse.Error.error(AppErrors.BINDING_VALIDATION_ERROR.code, error.defaultMessage ?: ""))
        }

        return handleException(AppErrors.BINDING_VALIDATION_ERROR, errors, e)
    }

    @ExceptionHandler(PageableBadRequestException::class)
    fun handlePageableBadRequestException(e: PageableBadRequestException, request: WebRequest): ResponseEntity<Any> =
        handleException(e.error, e.fieldName, e)

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException::class)
    fun handleExpiredToken(e: io.jsonwebtoken.ExpiredJwtException, request: WebRequest): ResponseEntity<Any> =
        handleException(AppErrors.TOKEN_EXPIRED_ERROR, e.message, e)

    @ExceptionHandler(io.jsonwebtoken.JwtException::class)
    fun handleInvalidToken (e: io.jsonwebtoken.JwtException, request: WebRequest): ResponseEntity<Any> =
        handleException(AppErrors.INVALID_TOKEN_ERROR, e.message, e)

    override fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException, headers: HttpHeaders,
                                              status: HttpStatus, request: WebRequest): ResponseEntity<Any> =
        handleException(AppErrors.BAD_REQUEST_ERROR, e.message, e)

    override fun handleHttpMediaTypeNotSupported(e: HttpMediaTypeNotSupportedException, headers: HttpHeaders,
                                                 status: HttpStatus, request: WebRequest): ResponseEntity<Any> =
        handleException(AppErrors.UNSUPPORTED_MEDIA_TYPE, e.message, e)

    override fun handleHttpMediaTypeNotAcceptable(e: HttpMediaTypeNotAcceptableException, headers: HttpHeaders,
                                                  status: HttpStatus, request: WebRequest): ResponseEntity<Any> =
        handleException(AppErrors.NOT_ACCEPTABLE_MEDIA_TYPE, e.message, e)

    override fun handleHttpRequestMethodNotSupported(e: HttpRequestMethodNotSupportedException, headers: HttpHeaders,
                                                     status: HttpStatus, request: WebRequest): ResponseEntity<Any> =
        handleException(AppErrors.METHOD_NOT_ALLOWED_ERROR, e.message, e)

    override fun handleNoHandlerFoundException(e: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus,
                                               request: WebRequest): ResponseEntity<Any> =
        handleException(AppErrors.NOT_FOUND_ERROR, e.message, e)

    private fun handleException(error: AppErrors, message: String?, e: Exception): ResponseEntity<Any> =
        handleException(error, arrayOf(message ?: ""), e)

    private fun handleException(error: AppErrors, params: Array<*>, e: Exception): ResponseEntity<Any> {
        val message: String = messageSource.getMessage(error, params)
        logger.error(message, e)

        return ResponseEntity.status(error.httpStatus)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(DResponse.notOk(error.code, message))
    }

    private fun handleException(error: AppErrors, errors: List<DResponse.Error>, e: Exception): ResponseEntity<Any> {
        logger.error("Error", e)

        return ResponseEntity.status(error.httpStatus)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(DResponse.notOk(errors))
    }
}
